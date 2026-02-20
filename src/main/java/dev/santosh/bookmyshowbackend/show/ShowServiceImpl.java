package dev.santosh.bookmyshowbackend.show;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl  implements  ShowService{

    private final ShowRepository showRepository;

    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public List<Show> getShows(Long movieId, String city) {
        return showRepository.findShowsByMovieAndCity(movieId, city);
    }

}
