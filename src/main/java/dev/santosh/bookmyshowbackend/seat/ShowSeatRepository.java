package dev.santosh.bookmyshowbackend.seat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

          List<ShowSeat> findByShowIdAndStatus(Long showId, SeatStatus status);


    List<ShowSeat> findByStatusAndLockedAtBefore(
            SeatStatus status,
            LocalDateTime time
    );

}
