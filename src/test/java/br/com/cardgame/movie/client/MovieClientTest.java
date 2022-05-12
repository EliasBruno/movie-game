package br.com.cardgame.movie.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@WireMockTest(httpPort = 9091)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class MovieClientTest {

    WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089));

    @Autowired
    private MovieClient movieClient;

    @Test
    void given_return_okay() {
        MovieOMD movieOMD = new MovieOMD();
        movieOMD.setImdbRating("2.3");
        movieOMD.setImdbVotes("2000");
        movieOMD.setTitle("Hulk");

        String url = "/?apikey=ba1b6518&t=Hulk&y=2019";
        WireMock.stubFor(WireMock.get(urlEqualTo(url))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(Json.write(movieOMD)))
        );

        MovieOMD movieOMDMock = movieClient.getMovie("Hulk", 2019);

        Assertions.assertNotNull(movieOMD);
        Assertions.assertEquals("Hulk", movieOMDMock.getTitle());
    }

    @Test
    void given_return_not_found() {
        MovieOMD movieOMD = new MovieOMD();
        movieOMD.setImdbRating("2.3");
        movieOMD.setImdbVotes("2000");
        movieOMD.setTitle("Hulk");

        String url = "/?apikey=ba1b6518&t=Test&y=2219";
        WireMock.stubFor(WireMock.get(urlEqualTo(url))
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(Json.write(movieOMD)))
        );

        Assertions.assertThrows(NotFound.class, () -> {
            movieClient.getMovie("Hulk", 2019);
        });
    }
}
