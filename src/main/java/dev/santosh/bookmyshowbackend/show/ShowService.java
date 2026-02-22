package dev.santosh.bookmyshowbackend.show;

import dev.santosh.bookmyshowbackend.dto.CreateShowRequest;

import java.util.List;

public interface ShowService {

    List<Show> getShows(Long movieId, String city);

    Show createShow(CreateShowRequest request);
}
