package br.com.cardgame.movie.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieClient {

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://www.omdbapi.com/apikey=ba1b6518";

    @CircuitBreaker(name = "Circuit", fallbackMethod = "getMovieFallback")
    @Retry(name = "RetryCallApi")
    public MovieOMD getMovie(String name, int year) {
        String param = "&t="+name+"&"+"y="+year;
        ResponseEntity<MovieOMD> response
                = restTemplate.getForEntity(url + param, MovieOMD.class);
        return response.getBody();
    }

    private String getMovieFallback(RuntimeException exception) {
        return exception.getMessage();
    }
}
