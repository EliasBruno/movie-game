package br.com.cardgame.movie.service;

import br.com.cardgame.movie.component.MovieComponent;
import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.MoviePair;
import br.com.cardgame.movie.repository.GameRepository;
import br.com.cardgame.movie.repository.MoviePairRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class GameServiceTest {
    @Mock
    GameRepository gameRepository;
    @Mock
    MoviePairRepository moviePairRepository;
    @Mock
    MovieComponent movieComponent;
    @Autowired
    private GameService gameService;

    @BeforeEach
    void setup() {
        gameRepository = mock(GameRepository.class);
        moviePairRepository = mock(MoviePairRepository.class);
        movieComponent = mock(MovieComponent.class);

        gameService = new GameService(moviePairRepository, gameRepository, movieComponent);
    }

    @Test
    void give_call_start_game() throws IllegalAccessException {

        when(gameRepository.findByEndIsNull()).thenReturn(false);
        Long[] ids = {12L};
        when(gameRepository.findByUser(Mockito.any())).thenReturn(ids);
        when(moviePairRepository.findAllByIdNotIn(ids)).thenReturn(getListMoviePair());
        gameService.start();
        Mockito.verify(gameRepository, times(1)).findByEndIsNull();
        Mockito.verify(gameRepository, times(1)).findByUser(Mockito.any());
        Mockito.verify(moviePairRepository, times(1)).findAllByIdNotIn(ids);

    }

    @Test
    void give_return_exception_in_start_game() {

        when(gameRepository.findByEndIsNull()).thenReturn(true);
        Assertions.assertThrows(IllegalAccessException.class,
            () -> gameService.start(),
            "Game not is finalization"
        );

    }

    @Test
    void give_call_okay_select() throws Exception {
        when(gameRepository.findById(12L)).thenReturn(Optional.ofNullable(getGame()));
        when(movieComponent.getMovieOkay(Mockito.any())).thenReturn("Hulk");
        gameService.select(12L, "Hulk");

        Mockito.verify(gameRepository, times(1)).findById(12L);
        Mockito.verify(movieComponent, times(1)).getMovieOkay(Mockito.any());
    }

    private ArrayList<Game> getListGame() {
        ArrayList<Game> games = new ArrayList();
        Game game = new Game();

        games.add(game);
        return games;
    }

    private ArrayList<MoviePair> getListMoviePair() {
        ArrayList<MoviePair> moviePairs = new ArrayList();
        MoviePair moviePair = new MoviePair();
        moviePair.setId(12L);
        moviePair.setNameA("Hulk");
        moviePair.setLaunchingA(2021);
        moviePair.setNameB("Matrix");
        moviePair.setLaunchingB(2014);
        moviePairs.add(moviePair);
        return moviePairs;
    }

    public Game getGame() {
        Game game = new Game();
        game.setId(12L);
        game.setStart(new Date());
        game.setMoviePair(getListMoviePair().get(0));
        return game;
    }
}
