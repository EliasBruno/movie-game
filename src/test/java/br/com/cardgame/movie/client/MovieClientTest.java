package br.com.cardgame.movie.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Json;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
public class MovieClientTest {
    public static WireMockServer wireMockRule = new WireMockServer(9090);

    @Autowired
    private MovieClient movieClient;

    @BeforeAll
    public void beforeAll() {
        wireMockRule.start();
        movieClient = new MovieClient();
    }

    @AfterAll
    public void afterAll() {
        wireMockRule.stop();
    }

    @AfterEach
    public void afterEach() {
        wireMockRule.resetMappings();
    }

    @Test
    void given_return_okey() {
        MovieOMD movieOMD = new MovieOMD();
        movieOMD.setImdbRating("2.3");
        movieOMD.setImdbVotes("2000");
        movieOMD.setTitle("Hulk");

        String url = "?apikey=ba1b6518&t=Hulk&y=2000";
        wireMockRule.stubFor(get(urlEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(Json.write(movieOMD)))
        );

        MovieOMD movieOMDMock = movieClient.getMovie("Hulk", 2019);

        wireMockRule.verify(
            getRequestedFor(urlEqualTo(url))
        );

        Assertions.assertEquals("Hulk", movieOMDMock.getTitle());
    }
}
