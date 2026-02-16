package dev.santosh.bookmyshowbackend.show;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("""
        select s
        from Show s
        join s.screen sc
        join sc.theatre t
        where s.movie.id = :movieId
          and t.city = :city
        order by s.startTime
    """)
    List<Show> findShowsByMovieAndCity(@Param("movieId") Long movieId,
                                       @Param("city") String city);
}

