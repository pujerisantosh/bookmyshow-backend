package dev.santosh.bookmyshowbackend.theatre;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
