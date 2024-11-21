package com.finalproject.portal.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WhiteList {

    private static final String[] WHITELIST_ENDPOINTS = {
            "/authentication/sign-in",
            "/authentication/sign-up",
            "/authentication/refresh-token",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public String[] getWhitelistEndpoints() {
        return WHITELIST_ENDPOINTS;
    }

}
