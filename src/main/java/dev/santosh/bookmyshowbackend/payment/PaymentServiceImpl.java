package dev.santosh.bookmyshowbackend.payment;

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


    @Override
    @Transactional
    public void processPayment(Long bookingId, BigDecimal amount) {

        // 1. Create payment (PENDING)
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentTime(LocalDateTime.now());

        paymentRepository.save(payment);

        // 2. Simulate payment success/failure
        boolean success = new Random().nextBoolean();

        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);

            // 👉 confirm booking (IMPORTANT)
            // TODO: we will connect this properly in next step

        } else {
            payment.setStatus(PaymentStatus.FAILED);

            // 👉 release seats (IMPORTANT)
            // TODO: optional improvement later
        }

        paymentRepository.save(payment);
    }

    }

