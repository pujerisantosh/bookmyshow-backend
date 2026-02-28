package dev.santosh.bookmyshowbackend.seat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository  extends JpaRepository<Seat,Long> {


    List<Seat> findByScreenId(Long screenId);

    Optional<Seat> findByScreenIdAndSeatNumber(Long screenId, String seatNumber);

    List<Seat> findByIdIn(List<Long> seatIds);
}
