package dev.santosh.bookmyshowbackend.booking.event;

import dev.santosh.bookmyshowbackend.booking.Booking;
import org.springframework.stereotype.Service;

@Service
public class BookingEventPublisher {


    public void publishBookingConfirmed(Booking booking){

        System.out.println("Booking Confirmed event Published for  bookingId:" +booking.getId());


    }

}
