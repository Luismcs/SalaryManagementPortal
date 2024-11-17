package com.finalproject.portal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigProperties {

    @Value("${secret-key.value}")
    private String secret;

    public String getSecret() {
        return secret;
    }

}
