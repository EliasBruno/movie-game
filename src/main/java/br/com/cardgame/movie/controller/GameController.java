package br.com.cardgame.movie.controller;

import br.com.cardgame.movie.controller.request.GameMoviePatch;
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

    @GetMapping("start")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Game> start() {
        try {
            Game game = gameService.start();
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/select")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Game> select(@PathVariable("id") final Long id, @RequestBody GameMoviePatch gameMoviePatch) {
        try {
            Game game = gameService.select(id, gameMoviePatch.movie);
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
