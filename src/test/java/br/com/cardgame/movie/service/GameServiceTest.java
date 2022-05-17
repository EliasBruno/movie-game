package br.com.cardgame.movie.service;

import br.com.cardgame.movie.component.MovieComponent;
import br.com.cardgame.movie.config.security.TokenService;
import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.Movie;
import br.com.cardgame.movie.entity.MoviePair;
import br.com.cardgame.movie.entity.User;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.servlet.http.HttpServletRequest;
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
    @Mock
    HttpServletRequest request;
    @Mock
    TokenService tokenService;
    @Autowired
    private GameService gameService;

    @BeforeEach
    void setup() {
        gameRepository = mock(GameRepository.class);
        moviePairRepository = mock(MoviePairRepository.class);
        movieComponent = mock(MovieComponent.class);
        request = mock(HttpServletRequest.class);
        tokenService = mock(TokenService.class);

        gameService = new GameService(
            moviePairRepository, gameRepository, movieComponent, request, tokenService
        );
    }

    @Test
    void give_call_start_game() {
        when(request.getHeader("Authorization")).thenReturn(getToken());
        when(tokenService.getUserId(any())).thenReturn(12L);
        when(gameRepository.findByEndIsNull(any())).thenReturn(new ArrayList<>());
        Long[] ids = {12L};
        when(gameRepository.findByUser(Mockito.any())).thenReturn(ids);
        when(moviePairRepository.findAllByIdNotIn(ids)).thenReturn(getListMoviePair());
        gameService.start();
        Mockito.verify(gameRepository, times(1)).findByEndIsNull(any());
        Mockito.verify(gameRepository, times(1)).findByUser(Mockito.any());
        Mockito.verify(moviePairRepository, times(1)).findAllByIdNotIn(ids);

    }

    @Test
    void give_call_okay_select_with_success() throws Exception {
        when(gameRepository.findByIdAndEndIsNull(12L)).thenReturn(Optional.ofNullable(getGame()));
        when(movieComponent.getMovieOkay(Mockito.any())).thenReturn("Hulk");
        gameService.select(12L, "Hulk");

        Mockito.verify(gameRepository, times(1)).findByIdAndEndIsNull(12L);
        Mockito.verify(movieComponent, times(1)).getMovieOkay(Mockito.any());
    }

    @Test
    void give_call_okay_select_with_exception() throws Exception {
        when(gameRepository.findByIdAndEndIsNull(12L)).thenReturn(Optional.ofNullable(getGame()));

        Assertions.assertThrows(Exception.class, () -> {
            gameService.select(1L, "Hulk");
        });
    }

    @Test
    void give_call_end_with_success() throws Exception {
        when(gameRepository.findById(12L)).thenReturn(Optional.ofNullable(getGame()));
        gameService.end(12L);

        Mockito.verify(gameRepository, times(1)).findById(12L);
    }

    @Test
    void give_call_end_with_exception() throws Exception {
        when(gameRepository.findById(12L)).thenReturn(Optional.ofNullable(getGame()));

        Assertions.assertThrows(Exception.class, () -> {
            gameService.end(1L);
        });
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
        Movie movieOne = new Movie();
        movieOne.setId(1L);
        movieOne.setName("Hulk");
        movieOne.setLaunching(2021);
        moviePair.setMovieOne(movieOne);
        Movie movieTwo = new Movie();
        movieTwo.setId(2L);
        movieTwo.setName("Matrix");
        movieTwo.setLaunching(2014);
        moviePair.setMovieTwo(movieTwo);
        moviePairs.add(moviePair);
        return moviePairs;
    }

    private Game getGame() {
        Game game = new Game();
        game.setId(12L);
        game.setStart(new Date());
        game.setMoviePair(getListMoviePair().get(0));
        return game;
    }

    private String getToken() {
        return "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJBUEkgR2FtZSBDYXJkIE1vdmllIiwic3ViIjoiMSIsImlhdCI6MTY1Mjc5MTUyOS" +
                "wiZXhwIjoxNjUyODc3OTI5fQ.MuwzA6Ki4qoCLKpqMgpByQRZBzoD5bd6-vUYQIYJvMrO2JUGJfKsTsEDJ0TIrK1QIbKNPn3tYQu3aK" +
                "VGtIGMfg";
    }
}
