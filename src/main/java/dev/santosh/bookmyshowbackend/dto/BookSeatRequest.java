package dev.santosh.bookmyshowbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookSeatRequest {

    private List<Long> showSeatIds;

    public BookSeatRequest() {
    }

    public List<Long> getShowSeatIds() {
        return showSeatIds;
    }

    public void setShowSeatIds(List<Long> showSeatIds) {
        this.showSeatIds = showSeatIds;
    }
}
