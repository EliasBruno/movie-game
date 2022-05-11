package br.com.cardgame.movie.component;

import br.com.cardgame.movie.client.MovieClient;
import br.com.cardgame.movie.client.MovieOMD;
import br.com.cardgame.movie.entity.MoviePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieComponent {
    @Autowired
    MovieClient movieClient;

    private Boolean calculatePoint(MovieOMD movieOMDA, MovieOMD movieOMDB) {
        Integer result1 = Integer.parseInt(movieOMDA.getImdbRating()) * Integer.parseInt(movieOMDA.getImdbVotes());
        Integer result2 = Integer.parseInt(movieOMDB.getImdbRating()) * Integer.parseInt(movieOMDB.getImdbVotes());
        return result1 > result2;
    }

    public String getMovieOkay(MoviePair moviePair) {
        moviePair.getNameA();
        MovieOMD movieOne = movieClient.getMovie(moviePair.getNameA(), moviePair.getLaunchingA());
        MovieOMD movieTwo = movieClient.getMovie(moviePair.getNameA(), moviePair.getLaunchingA());

        if(this.calculatePoint(movieOne, movieTwo)){
            return moviePair.getNameA();
        }
        return moviePair.getNameB();
    }
}
