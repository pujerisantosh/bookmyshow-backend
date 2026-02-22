package dev.santosh.bookmyshowbackend.theatre;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TheatreServiceImplTest {

    @Mock
    private  TheatreRepository theatreRepository;

    @InjectMocks
    private TheatreServiceImpl theatreService;

    @Test
    void shouldReturnTheatreByCity(){


        //given

        Theatre t1 = new Theatre();
        t1.setName("PVR");
        t1.setCity("Bangalore");

        Theatre t2 = new Theatre();
        t2.setName("INOX");
        t2.setCity("Bangalore");

        when(theatreRepository.findByCity("Bangalore"))
                .thenReturn(List.of(t1, t2));

        // when


        List<Theatre> result = theatreService.findByCity("Bangalore");


        //Then

        assertEquals(2, result.size());
        verify(theatreRepository).findByCity("Bangalore");

    }



}