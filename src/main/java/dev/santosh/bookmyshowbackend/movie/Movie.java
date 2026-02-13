package dev.santosh.bookmyshowbackend.movie;


import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer duration;

    //Required by JPA
    protected Movie() {

    }

    //  Your convenience constructor
    public Movie(String title, Integer duration) {

        this.title = title;
        this.duration = duration;


    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
