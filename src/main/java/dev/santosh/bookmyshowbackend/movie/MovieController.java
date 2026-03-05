package dev.santosh.bookmyshowbackend.movie;

import jakarta.persistence.GeneratedValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movies")
public class MovieController {

public final MovieService movieService;

    public MovieController(MovieService movieService) {

        this.movieService = movieService;
    }



    @PostMapping
    public Movie createMovie(@RequestBody Movie movie){


        return movieService.createMovie(movie);
    }


    @GetMapping
    public List<Movie> getMoviesByCity(@RequestParam String city){

        return movieService.getMoviesByCity(city);




    }
}
