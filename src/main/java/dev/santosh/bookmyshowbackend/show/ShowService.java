package dev.santosh.bookmyshowbackend.show;

import java.util.List;

public interface ShowService {

    List<Show> getShowsByMovieAndCity(Long movieId, String city);
}
