package dev.santosh.bookmyshowbackend.show;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }


    @GetMapping
    public List<Show> getShows(Long moveId, String city){


        return showRepository.findShowsByMovieAndCity(moveId, city);
    }
}
