package br.com.cardgame.movie.service;

import br.com.cardgame.movie.client.MovieClient;
import br.com.cardgame.movie.component.MovieComponent;
import br.com.cardgame.movie.config.security.TokenService;
import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.MoviePair;
import br.com.cardgame.movie.entity.User;
import br.com.cardgame.movie.repository.GameRepository;
import br.com.cardgame.movie.repository.MoviePairRepository;
import br.com.cardgame.movie.repository.custom.ScoreRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class GameService {
    public MoviePairRepository moviePairRepository;
    public GameRepository gameRepository;
    public MovieComponent movieComponent;
    @Autowired
    TokenService tokenService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    GameService(MoviePairRepository moviePairRepository, GameRepository gameRepository,
                MovieComponent movieComponent) {
        this.moviePairRepository = moviePairRepository;
        this.gameRepository = gameRepository;
        this.movieComponent = movieComponent;
    }

    public Game start() {
        String token = request.getHeader("Authorization");
        Long idUser = tokenService.getUserId(token.substring(7, token.length()));

        User user = new User();
        user.setId(idUser);
        Long[] gameIds = gameRepository.findByUser(user);
        ArrayList<MoviePair> moviesPair = moviePairRepository.findAllByIdNotIn(gameIds);
        Game game = new Game();
        List<Game> games = gameRepository.findByEndIsNull(user);
        if(games.size() > 0) return games.get(0);

        game.setMoviePair(moviesPair.get(0));
        game.setStart(new Date());
        game.setUser(user);
        return gameRepository.save(game);

    }

    public Game select(Long idGame, String movieSelected) throws Exception {
        Optional<Game> game = gameRepository.findByIdAndEndIsNull(idGame);
        if(game.isPresent()) {
            Game gameSave = game.get();
            String movieVoteMore= movieComponent.getMovieOkay(gameSave.getMoviePair());

            if(!movieVoteMore.equals(movieSelected)) {
                gameSave.setErrors(1);
                if(gameSave.getErrors() == 3) end(gameSave.getId());
            } else {
                gameSave.setSuccess(1);
            }

            gameSave.setSelected(movieSelected);

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

    public List<ScoreRanking> scoreRanking() {
        return gameRepository.findScoreRanking();
    }
}
