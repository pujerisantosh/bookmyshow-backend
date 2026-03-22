package dev.santosh.bookmyshowbackend.payment;

import java.math.BigDecimal;

public interface PaymentService {


    void processPayment(Long bookingId, BigDecimal amount);

   }
