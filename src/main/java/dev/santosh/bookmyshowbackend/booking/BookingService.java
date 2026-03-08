package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;

public interface BookingService {

    void bookSeats(BookSeatRequest request);
}
