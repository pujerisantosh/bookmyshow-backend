package dev.santosh.bookmyshowbackend.show;

import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;
import dev.santosh.bookmyshowbackend.dto.CreateShowRequest;
import dev.santosh.bookmyshowbackend.seat.ShowSeat;
import dev.santosh.bookmyshowbackend.seat.ShowSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowSeatService showSeatService;

    public ShowController(ShowService showService, ShowSeatService showSeatService) {

        this.showService = showService;
        this.showSeatService = showSeatService;
    }

    @GetMapping
    public List<Show> getShows(
            @RequestParam Long movieId,
            @RequestParam String city
    ) {
        return showService.getShows(movieId, city);
    }



    @PostMapping
    public Show createShow(@RequestBody CreateShowRequest createShowRequest){

return showService.createShow(createShowRequest);

    }


    @GetMapping("/{showId}/available-seats")
    public List<ShowSeat> getAvailableSeats(@PathVariable Long showId) {
        return showSeatService.getAvailableSeats(showId);
    }

    @PostMapping("/{showId}/book")
    public ResponseEntity<String> bookSeats(
            @PathVariable Long showId,
            @RequestBody BookSeatRequest request) {

        showSeatService.bookSeats(showId, request.getShowSeatIds());
        return ResponseEntity.ok("Seats booked successfully");
    }


}
