package com.furkhan.smartexpensetracker.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    // Change this later to a secure secret stored in application.properties
    private static final String SECRET_KEY = "mySecretKeyForSmartExpenseTracker123456789";

    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(
                        io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                        Jwts.SIG.HS256
                )
                .compact();
    }
    public String extractEmail(String token) {
    Claims claims = Jwts.parser()
            .verifyWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
            .build()
            .parseSignedClaims(token)
            .getPayload();

    return claims.getSubject();
}
}