package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.booking.event.BookingEventPublisher;
import dev.santosh.bookmyshowbackend.dto.BookSeatRequest;
import dev.santosh.bookmyshowbackend.dto.CreateBookingRequest;
import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import dev.santosh.bookmyshowbackend.seat.ShowSeat;
import dev.santosh.bookmyshowbackend.seat.ShowSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Booking createBooking(CreateBookingRequest request) {
        Booking booking = new Booking();
        booking.setShowId(request.getShowId());
        booking.setUserId(request.getUserId());
        booking.setShowSeatIds(request.getShowSeatIds());
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }


    @Transactional
    public Booking confirmBooking(Long bookingId) {

        // 1. Get existing booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2. Fetch seats
        List<ShowSeat> seats = showSeatRepository.findAllById(booking.getShowSeatIds());

        for (ShowSeat seat : seats) {
            if (seat.getStatus() != SeatStatus.LOCKED) {
                throw new RuntimeException("Seat not locked");
            }
            seat.setStatus(SeatStatus.BOOKED);
        }

        showSeatRepository.saveAll(seats);

        // 3. Update booking
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepository.save(booking);

        // 4. Publish event
        bookingEventPublisher.publishBookingConfirmed(savedBooking);

        return savedBooking;
    }

    @Override
    public void markBookingFailed(Long bookingId) {

    }


}
