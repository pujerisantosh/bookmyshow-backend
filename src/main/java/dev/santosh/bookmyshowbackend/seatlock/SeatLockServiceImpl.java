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
import java.util.concurrent.TimeUnit;

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

        for (Long seatId : seatIds) {

            String key = "seat_lock_" + seatId;

            // 🔥 Redis Lock
            Boolean locked = redisTemplate.opsForValue()
                    .setIfAbsent(key, userId.toString(), 5, TimeUnit.MINUTES);

            if (!Boolean.TRUE.equals(locked)) {
                throw new RuntimeException("Seat already locked: " + seatId);
            }
        }

        // 🔥 Now update DB
        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);

        for (ShowSeat seat : seats) {
            seat.setStatus(SeatStatus.LOCKED);
            seat.setLockedAt(LocalDateTime.now());
        }

        showSeatRepository.saveAll(seats);

        // 🔥 invalidate cache
        redisTemplate.delete("show_seats_" + showId);
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