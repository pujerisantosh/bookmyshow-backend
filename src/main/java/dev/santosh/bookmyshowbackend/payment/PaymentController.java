package dev.santosh.bookmyshowbackend.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public String makePayment(@RequestParam Long bookingId,
                              @RequestParam BigDecimal amount) {

        paymentService.processPayment(bookingId, amount);

        return "Payment processed";
    }


}
