package dev.santosh.bookmyshowbackend.seatlock;

import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import dev.santosh.bookmyshowbackend.seat.ShowSeat;

import java.util.List;


import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import dev.santosh.bookmyshowbackend.seat.ShowSeat;
import dev.santosh.bookmyshowbackend.seat.ShowSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatLockSyncService {

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(fixedRate = 60000) // every 1 min
    @Transactional
    public void syncSeatLocks() {

        System.out.println("Running Redis-DB sync job...");

        // 1. Get all LOCKED seats
        List<ShowSeat> lockedSeats =
                showSeatRepository.findByStatus(SeatStatus.LOCKED);

        for (ShowSeat seat : lockedSeats) {

            String key = "seat_lock_" + seat.getId();

            // 2. Check Redis
            Boolean exists = redisTemplate.hasKey(key);

            // 3. If Redis lock NOT present → release DB seat
            if (!Boolean.TRUE.equals(exists)) {

                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setLockedAt(null);

                System.out.println("Releasing seat: " + seat.getId());
            }
        }

        showSeatRepository.saveAll(lockedSeats);
    }
}

