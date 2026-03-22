package dev.santosh.bookmyshowbackend.seatlock;

import ch.qos.logback.core.status.Status;
import dev.santosh.bookmyshowbackend.seat.SeatStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seat_lock")
public class SeatLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long showId;
    private Long seatId;
    private Long userId;

    private LocalDateTime lockTime;
    private LocalDateTime expiryTime;


    @Enumerated(EnumType.STRING)
    private SeatLockStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public SeatLockStatus getStatus() {
        return status;
    }

    public void setStatus(SeatLockStatus status) {
        this.status = status;
    }
}
