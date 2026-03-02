package dev.santosh.bookmyshowbackend.seat;

import java.util.List;

public interface ShowSeatService {

    List<ShowSeat> getAvailableSeats(Long showId);

    void bookSeats(Long showId, List<Long> showSeatIds);


}
