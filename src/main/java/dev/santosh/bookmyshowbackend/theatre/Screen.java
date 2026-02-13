package dev.santosh.bookmyshowbackend.theatre;

import jakarta.persistence.*;

@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    // Required by JPA
    protected Screen() {
    }

    public Screen(String name, Theatre theatre) {
        this.name = name;
        this.theatre = theatre;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }
}
