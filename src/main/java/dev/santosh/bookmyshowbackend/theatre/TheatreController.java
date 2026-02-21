package dev.santosh.bookmyshowbackend.theatre;


import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/theatres")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }


    @PostMapping
public Theatre createTheatre(@RequestBody Theatre theatre){

return theatreService.createTheatre(theatre);

}


    @GetMapping("/city/{city}")
    public List<Theatre> getTheatresByCity(@PathVariable String city) {
        return theatreService.findByCity(city);
    }

}
