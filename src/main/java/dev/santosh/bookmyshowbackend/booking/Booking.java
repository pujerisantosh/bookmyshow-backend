package dev.santosh.bookmyshowbackend.booking;

import dev.santosh.bookmyshowbackend.seat.ShowSeat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;


    @OneToMany
    private List<ShowSeat> seats;


    public void setSeats(List<ShowSeat> seats) {
        this.seats = seats;
    }

    public void setStatus(BookingStatus bookingStatus) {
        this.status = bookingStatus;
    }


}
