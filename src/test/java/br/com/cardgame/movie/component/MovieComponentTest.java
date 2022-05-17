package br.com.cardgame.movie.component;

import br.com.cardgame.movie.client.MovieClient;
import br.com.cardgame.movie.client.MovieOMD;
import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.Movie;
import br.com.cardgame.movie.entity.MoviePair;
import br.com.cardgame.movie.repository.GameRepository;
import br.com.cardgame.movie.repository.MoviePairRepository;
import br.com.cardgame.movie.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class MovieComponentTest {

    @Mock
    MovieClient movieClient;
    @Autowired
    private MovieComponent movieComponent;

    @BeforeEach
    void setup() {
        movieClient = mock(MovieClient.class);
        movieComponent = new MovieComponent(movieClient);
    }

    @Test
    void give_call_start_game() throws Exception {
        MovieOMD movieOMDOne = new MovieOMD();
        movieOMDOne.setTitle("Hulk");
        movieOMDOne.setImdbVotes("7,652");
        movieOMDOne.setImdbRating("8.3");

        MovieOMD movieOMDTwo = new MovieOMD();
        movieOMDTwo.setTitle("Matrix");
        movieOMDTwo.setImdbVotes("6,652");
        movieOMDTwo.setImdbRating("5.3");

        Movie movieOne = getMoviePair().getMovieOne();
        Movie movieTwo = getMoviePair().getMovieTwo();
        when(movieClient.getMovie(movieOne.getName(), movieOne.getLaunching())).thenReturn(movieOMDOne);
        when(movieClient.getMovie(movieTwo.getName(), movieTwo.getLaunching())).thenReturn(movieOMDTwo);

        String name = movieComponent.getMovieOkay(getMoviePair());
        Mockito.verify(movieClient, times(1)).getMovie(movieOne.getName(), movieOne.getLaunching());
        Mockito.verify(movieClient, times(1)).getMovie(movieTwo.getName(), movieTwo.getLaunching());
        Assertions.assertEquals("Hulk", name);
    }

    private ArrayList<Game> getListGame() {
        ArrayList<Game> games = new ArrayList();
        Game game = new Game();

        games.add(game);
        return games;
    }

    private MoviePair getMoviePair() {
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

        return moviePair;
    }

}
