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

    private Long showId;

    private Long userId;

    // 🔥 Store only IDs (lightweight + scalable)
    @ElementCollection
    private List<Long> showSeatIds;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

}
