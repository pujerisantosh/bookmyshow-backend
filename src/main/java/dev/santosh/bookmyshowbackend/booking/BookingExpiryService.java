package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.seatlock.SeatLockService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingExpiryService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatLockService seatLockService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireBookings() {

        System.out.println("Running expiry job...");


        LocalDateTime expiryTime = LocalDateTime.now().minusSeconds(30);

        List<Booking> expiredBookings =
                bookingRepository.findByStatusAndCreatedAtBefore(
                        BookingStatus.PENDING,
                        expiryTime
                );

        System.out.println("Expired bookings count: " + expiredBookings.size());

        for (Booking booking : expiredBookings) {

            booking.setStatus(BookingStatus.FAILED);

            seatLockService.releaseSeats(booking.getShowSeatIds());
        }

        bookingRepository.saveAll(expiredBookings);
    }
}