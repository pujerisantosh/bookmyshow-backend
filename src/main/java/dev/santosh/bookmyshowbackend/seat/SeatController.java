package dev.santosh.bookmyshowbackend.seat;


import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class SeatController {

    private final ShowSeatService showSeatService;

    public SeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }


    @GetMapping("/{showId}/seats/available")
    public List<ShowSeat> getAvailableSeats(@PathVariable Long showId) {


        return showSeatService.getAvailableSeats(showId);

    }


    @PostMapping("/lock-seats")
    public String lockSeats(@RequestBody BookSeatRequest request){

      ShowSeatService.lockSeats(request.getShowSeatIds());

      return  "Seat Locked Successfully";


    }
}
