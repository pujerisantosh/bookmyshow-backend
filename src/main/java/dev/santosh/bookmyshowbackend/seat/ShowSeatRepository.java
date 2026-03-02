package dev.santosh.bookmyshowbackend.seat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

          List<ShowSeat> findByShowIdAndStatus(Long showId, SeatStatus status);

}
