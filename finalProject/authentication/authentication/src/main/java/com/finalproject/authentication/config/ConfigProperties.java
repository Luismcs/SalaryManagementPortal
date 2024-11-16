package com.finalproject.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

    @Value("${secret-key.value}")
    private String secret;

    @Value("${access-token-expiration-time.value}")
    private long accessTokenExpirationTime;

    @Value("${refresh-token-expiration-time.value}")
    private long refreshTokenExpirationTime;

    public String getSecret() {
        return secret;
    }

    public long getAccessTokenExpirationTime() {
        return accessTokenExpirationTime;
    }

    public long getRefreshTokenExpirationTime() {
        return refreshTokenExpirationTime;
    }
}
