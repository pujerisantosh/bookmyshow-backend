package dev.santosh.bookmyshowbackend.movie;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getMoviesByCity(String city) {
        return movieRepository.findMoviesByCity(city);
    }

    @Override
    public Movie createMovie(Movie movie) {
        movie.setId(null);
        return movieRepository.save(movie);
    }
}
