package br.com.cardgame.movie.repository;

import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Long[] findByUser(User user);
    Boolean findByEndIsNull();

}
