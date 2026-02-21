package dev.santosh.bookmyshowbackend.theatre;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreServiceImpl implements  TheatreService {

    private final TheatreRepository theatreRepository;

    public TheatreServiceImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }


    @Override
    public Theatre createTheatre(Theatre theatre){
        return theatreRepository.save(theatre);


    }


    @Override
    public List<Theatre> findByCity(String city){

        return theatreRepository.findByCity(city);


    }



}
