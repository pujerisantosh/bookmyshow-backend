package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }




    @PostMapping
    public String bookSeats(@RequestBody BookSeatRequest request){

        bookingService.bookSeats(request);
        return "Seat Booked Successfully";

    }
}
