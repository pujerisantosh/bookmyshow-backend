package dev.santosh.bookmyshowbackend.movie;

import java.util.List;

public interface MovieService {

    List<Movie> getMoviesByCity(String city);
}
