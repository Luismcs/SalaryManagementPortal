package com.finalproject.portal.service;

import com.finalproject.portal.config.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Map;

@Service
public class JwtServiceImpl {

    private final ConfigProperties configProperties;

    public JwtServiceImpl(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(configProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getIssuer();
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    public String extractCorrelationId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("correlation_id", String.class);
    }

    public boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token).getExpiration();
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getDetails() instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
            return (String) details.get("token");
        }
        return null;
    }

}
