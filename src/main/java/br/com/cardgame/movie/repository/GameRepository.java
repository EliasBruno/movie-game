package br.com.cardgame.movie.repository;

import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g.id FROM Game g WHERE g.user = ?1")
    Long[] findByUser(User user);

    @Query("SELECT g FROM Game g JOIN FETCH g.user u WHERE g.end is null")
    List<Game> findByEndIsNull();

    @Query("SELECT g FROM Game g JOIN FETCH g.user " +
            "JOIN FETCH g.moviePair m " +
            "JOIN FETCH m.movieOne o " +
            "JOIN FETCH m.movieTwo t WHERE g.id = ?1 AND g.end is null")
    Optional<Game> findByIdAndEndIsNull(Long id);

}
