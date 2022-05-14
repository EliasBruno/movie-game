package br.com.cardgame.movie.repository;

import br.com.cardgame.movie.entity.MoviePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MoviePairRepository extends JpaRepository<MoviePair, Long> {
    @Query("SELECT mp FROM MoviePair mp " +
            "JOIN FETCH mp.movieOne o " +
            "JOIN FETCH mp.movieTwo t" +
            " WHERE mp.id not in (?1)")
    ArrayList<MoviePair> findAllByIdNotIn(Long id[]);

}
