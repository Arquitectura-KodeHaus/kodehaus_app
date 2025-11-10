package com.kodehaus.stocksbackend.utils.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String correo) {
        return Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractCorreo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String correo) {
        return correo.equals(extractCorreo(token));
    }
}
