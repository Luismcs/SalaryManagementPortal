package com.finalproject.authentication.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JWTService {

    String generateToken(String username, String[] roles);

    Claims extractAllClaims(String token);

    Map<String, Object> getClaims(String username, String[] roles);

    String generateRefreshToken(String username, String[] roles);

    boolean isExpired(String token);

}
