package dev.santosh.bookmyshowbackend.seat;

import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

          List<ShowSeat> findByShowIdAndStatus(Long showId, SeatStatus status);


    List<ShowSeat> findByStatusAndLockedAtBefore(
            SeatStatus status,
            LocalDateTime time
    );

    ShowSeat findByShowIdAndSeatId(Long showId, Long seatId);

    // 🔥 CONCURRENCY
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ShowSeat s WHERE s.id IN :ids")
    List<ShowSeat> findAllByIdForUpdate(@Param("ids") List<Long> ids);


    @Query(value = "SELECT * FROM show_seats WHERE id IN (:ids) FOR UPDATE", nativeQuery = true)
    List<ShowSeat> findAllByIdForUpdateNative(@Param("ids") List<Long> ids);
}
