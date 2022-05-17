package br.com.cardgame.movie.controller;

import br.com.cardgame.movie.component.MovieComponent;
import br.com.cardgame.movie.controller.request.GameMoviePatch;
import br.com.cardgame.movie.controller.request.LoginRequest;
import br.com.cardgame.movie.controller.response.TokenResponse;
import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.entity.MoviePair;
import br.com.cardgame.movie.entity.User;
import br.com.cardgame.movie.repository.GameRepository;
import br.com.cardgame.movie.repository.MoviePairRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MoviePairRepository moviePairRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    MovieComponent movieComponent;
    private static String url = "/game-movie";
    private TokenResponse tokenResponse;

    public TokenResponse getToken() throws Exception {
        LoginRequest request = new LoginRequest("elias", "123456");
         MvcResult result = mockMvc.perform(post("/auth")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        return objectMapper.readValue(result.getResponse().getContentAsString(), TokenResponse.class);
    }

    @Test
    public void give_start_game_success() throws Exception {
        TokenResponse tokenResponse = getToken();
        mockMvc.perform(get(url+"/start")
                        .header("Authorization", "Bearer "+ tokenResponse.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void give_select_game_success() throws Exception {
        when(movieComponent.getMovieOkay(Mockito.any())).thenReturn("Batman");
        TokenResponse tokenResponse = getToken();
        save_game();
        GameMoviePatch gameMoviePatch = new GameMoviePatch();
        gameMoviePatch.movie = "Batman";
        MvcResult result= mockMvc.perform(patch(url+"/1/select")
                .header("Authorization", "Bearer "+ tokenResponse.getToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(gameMoviePatch)))
                .andExpect(status().isOk())
                .andReturn();
        Game game = objectMapper.readValue(result.getResponse().getContentAsString(), Game.class);
        Assertions.assertEquals("Batman", game.getSelected());
        Assertions.assertEquals(1, game.getSuccess());
    }

    @Test
    public void give_select_game_errors() throws Exception {
        when(movieComponent.getMovieOkay(Mockito.any())).thenReturn("Hulk");
        TokenResponse tokenResponse = getToken();
        save_game();
        GameMoviePatch gameMoviePatch = new GameMoviePatch();
        gameMoviePatch.movie = "Batman";
        MvcResult result= mockMvc.perform(patch(url+"/1/select")
                .header("Authorization", "Bearer "+ tokenResponse.getToken())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(gameMoviePatch)))
                .andExpect(status().isOk())
                .andReturn();
        Game game = objectMapper.readValue(result.getResponse().getContentAsString(), Game.class);
        Assertions.assertEquals("Batman", game.getSelected());
        Assertions.assertEquals(1, game.getErrors());
    }

    @Test
    public void give_end_game_success() throws Exception {
        save_game();
        mockMvc.perform(patch(url+"/1/end")
                        .contentType("application/json")
                        .content(""))
                .andExpect(status().isOk());
    }

    @Test
    public void give_score_ranking_game_success() throws Exception {
        mockMvc.perform(get(url+"/score-ranking"))
                .andExpect(status().isOk());
    }

    private void save_game() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Test");
        ArrayList<MoviePair> moviesPair = moviePairRepository.findAllByIdNotIn(new Long[]{1L});
        Game game = new Game();
        game.setMoviePair(moviesPair.get(0));
        game.setStart(new Date());
        game.setUser(user);
        gameRepository.save(game);
    }
}
