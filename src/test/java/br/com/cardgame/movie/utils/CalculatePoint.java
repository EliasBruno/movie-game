package br.com.cardgame.movie.utils;

import br.com.cardgame.movie.client.MovieOMD;

import java.math.BigDecimal;

public class CalculatePoint {

    private static Boolean calculate(MovieOMD movieOMDA, MovieOMD movieOMDB) {
        String voteA = movieOMDA.getImdbVotes().replace(",",".");
        BigDecimal resultA = new BigDecimal(movieOMDA.getImdbRating()).multiply(new BigDecimal(voteA));
        String voteB = movieOMDB.getImdbVotes().replace(",",".");
        BigDecimal resultB = new BigDecimal(movieOMDB.getImdbRating()).multiply(new BigDecimal(voteB));
        return resultA.compareTo(resultB) > 0;
    }

}
