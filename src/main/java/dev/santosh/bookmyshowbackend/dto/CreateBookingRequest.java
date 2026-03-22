package dev.santosh.bookmyshowbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateBookingRequest {

    private Long showId;
    private Long userId;
    private List<Long> showSeatIds;

}
