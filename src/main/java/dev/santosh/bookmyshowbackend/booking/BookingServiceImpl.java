package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.booking.event.BookingEventPublisher;
import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;
import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import dev.santosh.bookmyshowbackend.seat.ShowSeat;
import dev.santosh.bookmyshowbackend.seat.ShowSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final ShowSeatRepository showSeatRepository;

    private final BookingRepository bookingRepository;

    private final BookingEventPublisher bookingEventPublisher;

    public BookingServiceImpl(ShowSeatRepository showSeatRepository, BookingRepository bookingRepository, BookingEventPublisher bookingEventPublisher) {
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.bookingEventPublisher = bookingEventPublisher;
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


    @Transactional
    public Booking confirmBooking(List<Long> showSeatIds) {

        List<ShowSeat> seats = showSeatRepository.findAllById(showSeatIds);

        for (ShowSeat seat : seats) {
            if (seat.getStatus() != SeatStatus.LOCKED) {
                throw new RuntimeException("Seat not locked");
            }
            seat.setStatus(SeatStatus.BOOKED);
        }

        showSeatRepository.saveAll(seats);

        Booking newBooking = new Booking();
        newBooking.setSeats(seats);
        newBooking.setStatus(BookingStatus.CONFIRMED);

        Booking booking = bookingRepository.save(newBooking);

        // publish event
        bookingEventPublisher.publishBookingConfirmed(booking);

        return booking;
    }
}
