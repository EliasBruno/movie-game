package br.com.cardgame.movie.controller;

import br.com.cardgame.movie.config.security.TokenService;
import br.com.cardgame.movie.controller.request.LoginRequest;
import br.com.cardgame.movie.controller.response.TokenResponse;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken dataLogin = loginRequest.convert();
        try {
            System.out.println(SignatureAlgorithm.HS256);
            Authentication authentication = authenticationManager.authenticate(dataLogin);
            String token = tokenService.generate(authentication);
            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
