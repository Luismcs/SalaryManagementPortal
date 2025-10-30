package com.finalproject.authentication.service;

import com.finalproject.authentication.config.ConfigProperties;
import com.finalproject.authentication.dto.JWTResponseDTO;
import com.finalproject.authentication.dto.RefreshTokenDTO;
import com.finalproject.authentication.model.RefreshToken;
import com.finalproject.authentication.model.UserCredentials;
import com.finalproject.authentication.repository.UserCredentialsRepository;
import com.finalproject.authentication.service.impl.AuthenticationServiceImpl;
import com.finalproject.authentication.service.impl.JWTServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JWTServiceTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @Mock
    private ConfigProperties configProperties;

    @InjectMocks
    private JWTServiceImpl jwtService;

    private UserCredentials userCredentials;
    private String key;

    @BeforeEach
    void init() {

        key = "5b56922fe298dda484c28ac6f50faedf129ebfcb0d7a0929a3644d35ac0f3280";
        userCredentials = new UserCredentials();
        userCredentials.setUsername("johndoe");
        userCredentials.setPassword("$2a$10$TSwblC5u/WXhazgbTgNO/uUAsKKlDfecvupV41KvQ8vEwlyvEBe1.");
        userCredentials.setCorrelationId("1");

    }

    @Test
    void jwtServiceImpl_generateToken_returnsAccessToken() {

        when(configProperties.getAccessTokenExpirationTime()).thenReturn(86_400_000L);
        when(configProperties.getSecret()).thenReturn(key);
        when(userCredentialsRepository.findByUsername(userCredentials.getUsername()))
                .thenReturn(Optional.of(userCredentials));

        String token = jwtService.generateToken("johndoe", new String[]{"USER"});

        Claims claims = Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(configProperties.getSecret())))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertEquals("johndoe", claims.getSubject());
        assertEquals("USER", ((List<String>) claims.get("roles")).get(0));

    }

    @Test
    void jwtServiceImpl_getClaims_returnsMapStringObject() {
        when(userCredentialsRepository.findByUsername(userCredentials.getUsername())).thenReturn(Optional.ofNullable(userCredentials));
        Map<String, Object> claims = jwtService.getClaims(userCredentials.getUsername(), new String[]{"USER"});

        assertEquals("johndoe", claims.get("username"));
        assertEquals("USER", ((String[]) claims.get("roles"))[0]);
    }

    @Test
    void jwtServiceImpl_isExpired_returnsTrue() {
        when(configProperties.getSecret()).thenReturn(key);

        String token = Jwts.builder().expiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key)), Jwts.SIG.HS256)
                .compact();

        assertTrue(jwtService.isExpired(token));
    }

    @Test
    void jwtServiceImpl_isExpired_returnsFalse() {
        when(configProperties.getSecret()).thenReturn(key);

        String token = Jwts.builder().expiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key)), Jwts.SIG.HS256)
                .compact();

        assertFalse(jwtService.isExpired(token));
    }

}
