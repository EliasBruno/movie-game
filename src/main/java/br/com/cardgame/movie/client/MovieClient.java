package br.com.cardgame.movie.client;

import com.sun.xml.bind.v2.TODO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieClient {

    RestTemplate restTemplate = new RestTemplate();
    @Value("${omdbapi.api.url}")
    String url = "";

    @CircuitBreaker(name = "Circuit", fallbackMethod = "getMovieFallback")
    @Retry(name = "RetryCallApi")
    public MovieOMD getMovie(String name, int year) {
        String param = "/?apikey=ba1b6518&t="+name+"&"+"y="+year;
        ResponseEntity<MovieOMD> response
                = restTemplate.getForEntity(url + param, MovieOMD.class);
        return response.getBody();
    }

    private MovieOMD getMovieFallback(java.lang.String movie, int year, java.lang.Throwable exception) throws Exception {
        throw new Exception("Not Found movie "+ movie + "of year " + year + "| Error" + exception.getMessage());
    }
}
