package dev.santosh.bookmyshowbackend.show;

import dev.santosh.bookmyshowbackend.movie.Movie;
import dev.santosh.bookmyshowbackend.theatre.Screen;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Column(nullable = false)
    private LocalDateTime startTime;

    // Required by JPA
    protected Show() {
    }

    public Show(Movie movie, Screen screen, LocalDateTime startTime) {
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
