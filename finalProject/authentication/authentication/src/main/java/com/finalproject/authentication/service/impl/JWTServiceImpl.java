package com.finalproject.authentication.service.impl;

import com.finalproject.authentication.config.ConfigProperties;
import com.finalproject.authentication.model.UserCredentials;
import com.finalproject.authentication.repository.UserCredentialsRepository;
import com.finalproject.authentication.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class JWTServiceImpl implements JWTService {

    private final ConfigProperties configProperties;
    private final UserCredentialsRepository userCredentialsRepository;

    public JWTServiceImpl(ConfigProperties configProperties, UserCredentialsRepository userCredentialsRepository) {
        this.configProperties = configProperties;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public String generateToken(String username, String[] roles) {
        return Jwts
                .builder()
                .claims(getClaims(username, roles))
                .subject(username)
                .issuer(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + configProperties.getAccessTokenExpirationTime()))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
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

    public Map<String, Object> getClaims(String username, String[] roles) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("username", username);
        extraClaims.put("roles", roles);

        Optional<UserCredentials> userCredentials = userCredentialsRepository.findByUsername(username);
        userCredentials.ifPresent(credentials -> extraClaims.put("correlation_id", credentials.getCorrelationId()));

        return extraClaims;
    }

    public String generateRefreshToken(String username, String[] roles) {
        return Jwts
                .builder()
                .claims(getClaims(username, roles))
                .subject(username)
                .issuer(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + configProperties.getRefreshTokenExpirationTime()))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isExpired(String token) {
        try {
            extractAllClaims(token).getExpiration();

            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

}
