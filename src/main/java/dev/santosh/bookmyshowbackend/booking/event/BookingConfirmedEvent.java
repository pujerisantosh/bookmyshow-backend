package dev.santosh.bookmyshowbackend.booking.event;


import dev.santosh.bookmyshowbackend.booking.Booking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public class BookingConfirmedEvent {

    private Long bookingId;
    private Long showId;
    private List<Long> seatIds;
    private LocalDateTime createdAt;



}
