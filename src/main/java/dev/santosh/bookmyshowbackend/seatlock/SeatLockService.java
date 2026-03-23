package dev.santosh.bookmyshowbackend.seatlock;


import java.util.List;

public interface SeatLockService {


    void lockSeats(Long showId, List<Long> seatIds, Long userId);

    void releaseExpiredLocks();

    void confirmSeats(Long showId, List<Long> seatIds, Long userId);

    void releaseSeats(List<Long> showSeatIds);


}
