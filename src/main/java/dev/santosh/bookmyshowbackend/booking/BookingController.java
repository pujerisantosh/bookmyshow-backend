package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;
import dev.santosh.bookmyshowbackend.dto.CreateBookingRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // 🔥 OLD FLOW (can keep or remove later)
    @PostMapping("/book")
    public String bookSeats(@RequestBody BookSeatRequest request) {
        bookingService.bookSeats(request);
        return "Seat Booked Successfully";
    }

    // 🔥 NEW FLOW (IMPORTANT)
    @PostMapping("/create")
    public Booking createBooking(@RequestBody CreateBookingRequest request) {
        return bookingService.createBooking(request);
    }
}