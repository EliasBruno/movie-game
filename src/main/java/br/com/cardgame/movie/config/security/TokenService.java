package br.com.cardgame.movie.config.security;

import br.com.cardgame.movie.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${cardgamemovie.jwt.expiration}")
    private String expiration;

    @Value("${cardgamemovie.jwt.secret}")
    private String secret;

    public String generate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("API Game Card Movie")
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Long.parseLong(expiration)))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }
}
