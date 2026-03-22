package dev.santosh.bookmyshowbackend.seat;

import dev.santosh.bookmyshowbackend.show.Show;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowSeatServiceImpl implements ShowSeatService {


    @Autowired
    private final ShowSeatRepository showSeatRepository;

    @Autowired
    private final SeatRepository seatRepository;

    public ShowSeatServiceImpl(ShowSeatRepository showSeatRepository, SeatRepository seatRepository) {
        this.showSeatRepository = showSeatRepository;
        this.seatRepository = seatRepository;
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

    @Override
    @Transactional
    public void createShowSeats(Show show) {

        // 1. Fetch all seats of screen
        List<Seat> seats = seatRepository.findByScreenId(show.getScreen().getId());

        if (seats.isEmpty()) {
            throw new RuntimeException("No seats found for screen");
        }

        // 2. Create show seats
        List<ShowSeat> showSeats = seats.stream()
                .map(seat -> {
                    ShowSeat ss = new ShowSeat();
                    ss.setShow(show);
                    ss.setSeat(seat);
                    ss.setStatus(SeatStatus.AVAILABLE);
                    return ss;
                })
                .toList();

        // 3. Save all
        showSeatRepository.saveAll(showSeats);
    }


    @Transactional
    public void lockSeats(List<Long> showSeatIds) {

        List<ShowSeat> seats = showSeatRepository.findAllById(showSeatIds);

        for (ShowSeat seat : seats) {

            if (seat.getStatus() != SeatStatus.AVAILABLE) {

                throw new RuntimeException("Seat is not available");
            }

            seat.setStatus(SeatStatus.LOCKED);

            seat.setLockedAt(LocalDateTime.now());

        }

        showSeatRepository.saveAll(seats);


    }


}
