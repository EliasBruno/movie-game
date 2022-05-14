package br.com.cardgame.movie.component;

import br.com.cardgame.movie.client.MovieClient;
import br.com.cardgame.movie.client.MovieOMD;
import br.com.cardgame.movie.entity.Movie;
import br.com.cardgame.movie.entity.MoviePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MovieComponent {
    @Autowired
    MovieClient movieClient;

    private Boolean calculatePoint(MovieOMD movieOMDA, MovieOMD movieOMDB) {
        String voteA = movieOMDA.getImdbVotes().replace(",",".");
        BigDecimal resultA = new BigDecimal(movieOMDA.getImdbRating()).multiply(new BigDecimal(voteA));
        String voteB = movieOMDB.getImdbVotes().replace(",",".");
        BigDecimal resultB = new BigDecimal(movieOMDB.getImdbRating()).multiply(new BigDecimal(voteB));
        return resultA.compareTo(resultB) > 0;
    }

    public String getMovieOkay(MoviePair moviePair) {
        Movie movieOne = moviePair.getMovieOne();
        MovieOMD movieOMDOne = movieClient.getMovie(movieOne.getName(), movieOne.getLaunching());
        Movie movieTwo = moviePair.getMovieTwo();
        MovieOMD movieOMDTwo = movieClient.getMovie(movieTwo.getName(), movieTwo.getLaunching());

        if(this.calculatePoint(movieOMDOne, movieOMDTwo)){
            return movieOne.getName();
        }
        return movieTwo.getName();
    }
}
