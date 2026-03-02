package dev.santosh.bookmyshowbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookSeatRequest {

    private final List<Long> showSeatIds;


    public BookSeatRequest(List<Long> showSeatIds) {
        this.showSeatIds = showSeatIds;
    }
}
