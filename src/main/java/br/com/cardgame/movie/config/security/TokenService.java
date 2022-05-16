package br.com.cardgame.movie.config.security;

import br.com.cardgame.movie.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build().parseClaimsJws(token);
            return  true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build().parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
