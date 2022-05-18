package br.com.cardgame.movie.controller;

import br.com.cardgame.movie.controller.request.GameMoviePatch;
import br.com.cardgame.movie.entity.Game;
import br.com.cardgame.movie.repository.custom.ScoreRanking;
import br.com.cardgame.movie.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/game-movie")
public class GameController {
    private GameService gameService;

    @Autowired
    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/start")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(
            summary = "Start Game",
            description = "Start Game for select movie.",
            tags = { "Game" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
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
    @Operation(
            summary = "Game Select",
            description = "Register movie selected.",
            tags = { "Game" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Game> select(@PathVariable("id") final Long id, @RequestBody GameMoviePatch gameMoviePatch) {
        try {
            Game game = gameService.select(id, gameMoviePatch.getMovie());

            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/end")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(
            summary = "Finishing Game",
            description = "Finishing Game of player.",
            tags = { "Game" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<?> end(@PathVariable("id") final Long id) {
        try {
            Game game = gameService.end(id);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/score-ranking")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(
            summary = "Score Game",
            description = "Show score of game all.",
            tags = { "Game" },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<ScoreRanking>> scoreRanking() {
        try {
            List<ScoreRanking> scoreRanking= gameService.scoreRanking();
            return ResponseEntity.ok(scoreRanking);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
