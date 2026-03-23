package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;
import dev.santosh.bookmyshowbackend.dto.CreateBookingRequest;

public interface BookingService {

    void bookSeats(BookSeatRequest request);

    Booking createBooking(CreateBookingRequest request);

    Booking confirmBooking(Long bookingId);

    void markBookingFailed(Long bookingId);
}
