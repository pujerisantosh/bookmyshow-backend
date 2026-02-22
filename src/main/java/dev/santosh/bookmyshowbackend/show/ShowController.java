package dev.santosh.bookmyshowbackend.show;

import dev.santosh.bookmyshowbackend.dto.CreateShowRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
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
}
