package br.com.cardgame.movie.controller;

import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.service.GameService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game-movie")
public class GameController {
    private GameService gameService;

    @Autowired
    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Game> start() {
        try {
            Game game = gameService.start();
            return ResponseEntity.ok(game);
        } catch (IllegalAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/{movie}/select")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Game> select(@PathVariable("id") final Long id, @PathVariable("movie") final String movie) {
        try {
            Game game = gameService.select(id, movie);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/end")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> end(@PathVariable("id") final Long id) {
        try {
            Game game = gameService.end(id);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
