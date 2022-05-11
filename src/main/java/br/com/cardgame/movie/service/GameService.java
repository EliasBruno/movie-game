package br.com.cardgame.movie.service;

import br.com.cardgame.movie.client.MovieClient;
import br.com.cardgame.movie.component.MovieComponent;
import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.MoviePair;
import br.com.cardgame.movie.entity.User;
import br.com.cardgame.movie.repository.GameRepository;
import br.com.cardgame.movie.repository.MoviePairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    public MoviePairRepository moviePairRepository;
    public GameRepository gameRepository;
    public MovieComponent movieComponent;

    @Autowired
    GameService(MoviePairRepository moviePairRepository, GameRepository gameRepository,
                MovieComponent movieComponent) {
        this.moviePairRepository = moviePairRepository;
        this.gameRepository = gameRepository;
        this.movieComponent = movieComponent;
    }

    public Game start() throws IllegalAccessException {
        if(gameRepository.findByEndIsNull()) {
            throw new IllegalAccessException("Game not is finalization");
        }

        Long[] gameIds = gameRepository.findByUser(new User());
        ArrayList<MoviePair> moviesPair = moviePairRepository.findAllByIdNotIn(gameIds);

        Game game = new Game();
        game.setMoviePair(moviesPair.get(0));
        game.setStart(new Date());
        return gameRepository.save(game);
    }

    public Game select(Long idGame, String movie) throws Exception {
        Optional<Game> game = gameRepository.findById(idGame);
        if(game.isPresent()) {
            Game gameSave = game.get();
            String movieOkay = movieComponent.getMovieOkay(gameSave.getMoviePair());

            if(movieOkay == movie) gameSave.setErrors(1);

            if(gameSave.getErrors() == 3) end(gameSave.getId());

            gameSave.setSelected(movie);

            return gameRepository.save(gameSave);
        }
        throw new Exception("Game Not Found");
    }

    public Game end(Long idGame) throws Exception {
        Optional<Game> game = gameRepository.findById(idGame);
        if(game.isPresent()) {
            Game gameSave = game.get();
            gameSave.setEnd(new Date());
            return gameRepository.save(gameSave);
        }
        throw new Exception("Game Not Found");
    }

}
