package dev.santosh.bookmyshowbackend.seatlock;

import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import dev.santosh.bookmyshowbackend.seat.ShowSeat;
import dev.santosh.bookmyshowbackend.seat.ShowSeatRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatLockServiceImpl implements SeatLockService {

    @Autowired
    private SeatLockRepository seatLockRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public void lockSeats(Long showId, List<Long> seatIds, Long userId) {

        // 🔥 DB LEVEL LOCK (CRITICAL)
        List<ShowSeat> seats = showSeatRepository.findAllByIdForUpdateNative(seatIds);

        // ✅ Validate
        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("Invalid seats selected");
        }

        for (ShowSeat seat : seats) {

            if (!seat.getShow().getId().equals(showId)) {
                throw new RuntimeException("Seat does not belong to this show");
            }

            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new RuntimeException("Seat already locked/booked: " + seat.getId());
            }
        }

        // ✅ Update + create locks
        for (ShowSeat seat : seats) {

            seat.setStatus(SeatStatus.LOCKED);
            seat.setLockedAt(LocalDateTime.now());

            SeatLock lock = new SeatLock();
            lock.setShowId(showId);
            lock.setSeatId(seat.getId());
            lock.setUserId(userId);
            lock.setLockTime(LocalDateTime.now());
            lock.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            lock.setStatus(SeatLockStatus.ACTIVE);
            redisTemplate.delete("show_seats_" + showId);

            seatLockRepository.save(lock);
        }

        // ✅ Save once
        showSeatRepository.saveAll(seats);
    }

    @Override
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredLocks() {

        List<SeatLock> expiredLocks =
                seatLockRepository.findByStatusAndExpiryTimeBefore(
                        SeatLockStatus.ACTIVE,
                        LocalDateTime.now()
                );

        for (SeatLock lock : expiredLocks) {

            ShowSeat seat = showSeatRepository
                    .findByShowIdAndSeatId(lock.getShowId(), lock.getSeatId());

            if (seat != null) {
                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setLockedAt(null);
                showSeatRepository.save(seat);
            }

            lock.setStatus(SeatLockStatus.EXPIRED);
        }

        seatLockRepository.saveAll(expiredLocks);
    }

    @Override
    @Transactional
    public void confirmSeats(Long showId, List<Long> seatIds, Long userId) {

        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);


        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("Invalid seats selected");
        }

        for (ShowSeat seat : seats) {

            if (!seat.getShow().getId().equals(showId)) {
                throw new RuntimeException("Seat does not belong to this show");
            }

            if (seat.getStatus() != SeatStatus.LOCKED) {
                throw new RuntimeException("Seat not locked: " + seat.getId());
            }

            // 🔥 Optional (recommended)
            // check lock expiry
            if (seat.getLockedAt() != null &&
                    seat.getLockedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Seat lock expired: " + seat.getId());
            }

            seat.setStatus(SeatStatus.BOOKED);
        }

        showSeatRepository.saveAll(seats);
    }

    @Override
    @Transactional
    public void releaseSeats(List<Long> showSeatIds) {
        List<ShowSeat> seats = showSeatRepository.findAllById(showSeatIds);

        for (ShowSeat seat : seats) {

            if (seat.getStatus() == SeatStatus.LOCKED) {
                redisTemplate.delete("show_seats_" + 2);

                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setLockedAt(null);
            }
        }

        showSeatRepository.saveAll(seats);
    }
}