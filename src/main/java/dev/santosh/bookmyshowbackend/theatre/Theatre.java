package dev.santosh.bookmyshowbackend.theatre;

import dev.santosh.bookmyshowbackend.movie.Movie;
import jakarta.persistence.*;

@Entity
@Table(name = "theatres")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;


    protected Theatre() {


    }


    public Theatre(String name, String city) {

        this.name = name;
        this.city = city;


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
