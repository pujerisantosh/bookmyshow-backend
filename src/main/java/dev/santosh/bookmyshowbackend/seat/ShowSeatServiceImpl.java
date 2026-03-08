package dev.santosh.bookmyshowbackend.seat;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowSeatServiceImpl implements ShowSeatService{


    private final  ShowSeatRepository showSeatRepository;

    public ShowSeatServiceImpl(ShowSeatRepository showSeatRepository) {
        this.showSeatRepository = showSeatRepository;
    }


    @Override
    public List<ShowSeat> getAvailableSeats(Long showId) {
        return showSeatRepository.findByShowIdAndStatus(showId, SeatStatus.AVAILABLE);
    }


    @Transactional
    public void bookSeats(Long showId, List<Long> showSeatIds) {

        List<ShowSeat> seats = showSeatRepository.findAllById(showSeatIds);

        if (seats.size() != showSeatIds.size()) {
            throw new RuntimeException("Invalid seats selected");
        }

        for (ShowSeat seat : seats) {
            if (!seat.getShow().getId().equals(showId)) {
                throw new RuntimeException("Seat does not belong to this show");
            }

            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new RuntimeException("Seat already booked");
            }

            seat.setStatus(SeatStatus.BOOKED);
        }

        showSeatRepository.saveAll(seats);

    }


    @Transactional
    public void lockSeats(List<Long> showSeatIds){

        List<ShowSeat> seats = showSeatRepository.findAllById(showSeatIds);

        for (ShowSeat seat : seats){

            if (seat.getStatus() != SeatStatus.AVAILABLE){

                throw  new RuntimeException("Seat is not available");
            }

            seat.setStatus(SeatStatus.LOCKED);

            seat.setLockedAT(LocalDateTime.now());

        }

        showSeatRepository.saveAll(seats);


    }
}
