package dev.santosh.bookmyshowbackend.scheduler;

import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import dev.santosh.bookmyshowbackend.seat.ShowSeat;
import dev.santosh.bookmyshowbackend.seat.ShowSeatRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SeatUnlockScheduler {

        private final ShowSeatRepository showSeatRepository;

        public SeatUnlockScheduler(ShowSeatRepository showSeatRepository) {
            this.showSeatRepository = showSeatRepository;
        }

        @Scheduled(fixedRate = 60000)
        public void unlockExpiredSeats() {

            LocalDateTime expiryTime =
                    LocalDateTime.now().minusMinutes(5);

            List<ShowSeat> expiredSeats =
                    showSeatRepository.findByStatusAndLockedAtBefore(
                            SeatStatus.LOCKED,
                            expiryTime
                    );

            for (ShowSeat seat : expiredSeats) {

                seat.setStatus(SeatStatus.AVAILABLE);
                seat.setLockedAt(null);
            }

            showSeatRepository.saveAll(expiredSeats);

            System.out.println("Unlocked seats count: " + expiredSeats.size());
        }
}

