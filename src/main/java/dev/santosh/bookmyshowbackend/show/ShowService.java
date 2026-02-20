package dev.santosh.bookmyshowbackend.show;

import java.util.List;

public interface ShowService {

    List<Show> getShows(Long movieId, String city);
}
