
package com.example.demo.Security;

import com.example.demo.Entity.UserRoles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Token expiration time: 30 minutes
    private static final long EXPIRATION_TIME = 1000 * 60 * 30;

    // Secret key (minimum 32 bytes for HS256)
    private static final String SECRET_KEY =
            "replace-this-with-a-new-random-secret-key-of-at-least-32-bytes";

    private final SecretKey key = Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8)
    );

    // Generate JWT Token
    public String generateToken(String username, UserRoles role) {

        Date now = new Date();

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + EXPIRATION_TIME))
                .claim("role", role.name())
                .signWith(key)
                .compact();
    }

    // Extract all claims from token
    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract username
    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    // Validate JWT token
    public boolean isTokenValid(
            String username,
            UserDetails user,
            String token
    ) {

        try {

            Claims claims = extractClaims(token);

            return username.equals(user.getUsername())
                    && claims.getExpiration().after(new Date());

        } catch (Exception e) {

            return false;
        }
    }
}
