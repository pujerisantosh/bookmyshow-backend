package dev.santosh.bookmyshowbackend.payment;

import dev.santosh.bookmyshowbackend.booking.Booking;
import dev.santosh.bookmyshowbackend.booking.BookingRepository;
import dev.santosh.bookmyshowbackend.booking.BookingService;
import dev.santosh.bookmyshowbackend.seatlock.SeatLockRepository;
import dev.santosh.bookmyshowbackend.seatlock.SeatLockService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SeatLockService seatLockService;


    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;


    @Override
    @Transactional
    public void processPayment(Long bookingId, BigDecimal amount) {

        // 1. Create payment
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentTime(LocalDateTime.now());

        paymentRepository.save(payment);

        // 2. Simulate payment
        boolean success = new Random().nextBoolean();

        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);

            // 🔥 confirm booking
            bookingService.confirmBooking(bookingId);

        } else {
            payment.setStatus(PaymentStatus.FAILED);

            // 🔥 fetch booking
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            // mark booking failed
            bookingService.markBookingFailed(bookingId);

            // 🔥 release seats
            seatLockService.releaseSeats(booking.getShowSeatIds());
        }

        // 3. Save final status
        paymentRepository.save(payment);
    }


    }

