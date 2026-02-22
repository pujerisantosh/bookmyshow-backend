package dev.santosh.bookmyshowbackend.show;

import dev.santosh.bookmyshowbackend.dto.CreateShowRequest;
import dev.santosh.bookmyshowbackend.movie.Movie;
import dev.santosh.bookmyshowbackend.movie.MovieRepository;
import dev.santosh.bookmyshowbackend.screen.ScreenRepository;
import dev.santosh.bookmyshowbackend.theatre.Screen;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    private final MovieRepository movieRepository;

    private final ScreenRepository screenRepository;

    public ShowServiceImpl(ShowRepository showRepository, MovieRepository movieRepository, ScreenRepository screenRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
    }

    @Override
    public List<Show> getShows(Long movieId, String city) {
        return showRepository.findShowsByMovieAndCity(movieId, city);
    }

    @Override
    public Show createShow(CreateShowRequest request) {


        // Fetch movie

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));



        // 2. Fetch Screen
        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));


        // 3. Create Show
        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());


        // 4. Save to DB
        return showRepository.save(show);
    }


}
