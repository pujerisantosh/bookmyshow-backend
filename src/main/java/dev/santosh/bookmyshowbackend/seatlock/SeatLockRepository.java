package dev.santosh.bookmyshowbackend.seatlock;

import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatLockRepository extends JpaRepository<SeatLock, Long> {

    List<SeatLock> findByStatusAndExpiryTimeBefore(SeatLockStatus status, LocalDateTime time);
}
