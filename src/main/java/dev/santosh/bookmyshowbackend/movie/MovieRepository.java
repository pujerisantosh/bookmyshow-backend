package dev.santosh.bookmyshowbackend.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {


    @Query("""
        select distinct m
        from Show s
        join s.movie m
        join s.screen sc
        join sc.theatre t
        where t.city = :city
    """)
    List<Movie> findMoviesByCity(@Param("city") String city);
}
