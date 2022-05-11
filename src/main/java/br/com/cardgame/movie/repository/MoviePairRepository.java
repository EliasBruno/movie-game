package br.com.cardgame.movie.repository;

import br.com.cardgame.movie.entity.MoviePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MoviePairRepository extends JpaRepository<MoviePair, Long> {
    ArrayList<MoviePair> findAllByIdNotIn(Long id[]);

}
