package ru.skypro.homework.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {
    @Value(("${jwt.secret}"))
    private String secret;
    @Value(("${jwt.lifetime}"))
    private Duration lifetime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> data = new HashMap<>();
        List<String> role = userDetails.getAuthorities().stream()
                                       .map(GrantedAuthority::getAuthority)
                                       .collect(Collectors.toList());
        data.put("role", role);
        data.put("username", userDetails.getUsername());
        data.put("password", userDetails.getPassword());
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifetime.toMillis());
        SecretKey key = getPublicSigningKey();
        return Jwts.builder()
                   .claims(data)
                   .subject(userDetails.getUsername())
                   .issuedAt(issuedDate)
                   .expiration(expiredDate)
                   .signWith(key)
                   .compact();
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getPassword(String token) {
        return getClaimsFromToken(token).get("password", String.class);
    }

    public List<String> getRole(String token) {
        return (List<String>) getClaimsFromToken(token).get("role", List.class);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                   .verifyWith(getPublicSigningKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    private SecretKey getPublicSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
