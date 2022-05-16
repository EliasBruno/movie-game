package br.com.cardgame.movie.repository;

import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.User;
import br.com.cardgame.movie.repository.custom.ScoreRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g.id FROM Game g WHERE g.user = ?1")
    Long[] findByUser(User user);

    @Query("SELECT g FROM Game g INNER JOIN g.user u WHERE g.user = ?1 AND g.end is null")
    List<Game> findByEndIsNull(User user);

    @Query("SELECT g FROM Game g INNER JOIN g.user " +
            "INNER JOIN FETCH g.moviePair m " +
            "INNER JOIN FETCH m.movieOne o " +
            "INNER JOIN FETCH m.movieTwo t WHERE g.id = ?1 AND g.end is null")
    Optional<Game> findByIdAndEndIsNull(Long id);

    @Query("SELECT new br.com.cardgame.movie.repository.custom.ScoreRanking(g.user, SUM(g.success) * COUNT(mp.id)) " +
            "FROM Game g INNER JOIN g.user u INNER JOIN g.moviePair mp " +
            "group by g.user")
    List<ScoreRanking> findScoreRanking();

}
