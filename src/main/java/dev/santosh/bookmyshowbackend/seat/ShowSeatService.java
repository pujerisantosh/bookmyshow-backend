package dev.santosh.bookmyshowbackend.seat;

import dev.santosh.bookmyshowbackend.show.Show;

import java.util.List;

public interface ShowSeatService {

    static void lockSeats(List<Long> showSeatIds) {
    }

    List<ShowSeat> getAvailableSeats(Long showId);

    void bookSeats(Long showId, List<Long> showSeatIds);


    public void createShowSeats(Show show);


}
