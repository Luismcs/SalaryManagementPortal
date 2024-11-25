package com.finalproject.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class JWTResponseDTO {

    @Schema(description = "The JWTResponse's accessToken", example = "eyJhbGciOiJIUzI1...", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The JWTResponse accessToken cannot be empty")
    private String accessToken;

    @Schema(description = "The JWTResponse's refreshToken", example = "eyJhbGciOiJIUzI1...", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The JWTResponse refreshToken cannot be empty")
    private String refreshToken;

    public JWTResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public JWTResponseDTO() {

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
