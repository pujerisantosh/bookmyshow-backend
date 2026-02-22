package dev.santosh.bookmyshowbackend.screen;

import dev.santosh.bookmyshowbackend.theatre.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository  extends JpaRepository<Screen, Long> {


}
