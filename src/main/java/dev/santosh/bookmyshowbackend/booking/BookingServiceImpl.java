package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;
import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import dev.santosh.bookmyshowbackend.seat.ShowSeat;
import dev.santosh.bookmyshowbackend.seat.ShowSeatRepository;
import dev.santosh.bookmyshowbackend.seat.ShowSeatService;
import dev.santosh.bookmyshowbackend.show.Show;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final ShowSeatRepository showSeatRepository;

    public BookingServiceImpl(ShowSeatRepository showSeatRepository) {
        this.showSeatRepository = showSeatRepository;
    }


    @Transactional
    public void bookSeats(BookSeatRequest request) {

        List<ShowSeat> seats = showSeatRepository.findAllById(request.getShowSeatIds());

        for (ShowSeat seat : seats) {

            if (seat.getStatus() == SeatStatus.BOOKED) {

                throw new RuntimeException("Seat is already booked");
            }

            seat.setStatus(SeatStatus.BOOKED);
        }


        showSeatRepository.saveAll(seats);


    }
}
