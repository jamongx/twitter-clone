package com.jason.twitter.userservice.security;

import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
//import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+jwtExpirationDate);

//        String token = Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(expireDate)
//                .signWith(secretKey())
//                .compact();

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(secretKey())
                .compact();

        return token;
    }

//    private Key key() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Get username fro JWT token
    public String getUsername(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(secretKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();

        Claims claims = Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();
        return username;
    }

    // Validate JWT Token
    public boolean validateToken(String token) {
//        Jwts.parserBuilder()
//                .setSigningKey(secretKey())
//                .build()
//                .parse(token);

        Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parse(token);

        return true;
    }
}
