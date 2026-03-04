package dev.santosh.bookmyshowbackend.seat;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
