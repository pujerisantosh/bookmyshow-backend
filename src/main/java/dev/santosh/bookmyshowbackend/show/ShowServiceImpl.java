package dev.santosh.bookmyshowbackend.show;

import java.util.List;

public class ShowServiceImpl  implements  ShowService{

    private final ShowRepository showRepository;

    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public List<Show> getShowsByMovieAndCity(Long movieId, String city) {
        return showRepository.findShowsByMovieAndCity(movieId,city);
    }
}
