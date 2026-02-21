package dev.santosh.bookmyshowbackend.theatre;

import java.util.List;

public interface TheatreService {

    Theatre createTheatre(Theatre theatre);

    public List<Theatre> findByCity(String city);
}
