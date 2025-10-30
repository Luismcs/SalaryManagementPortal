package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignInDTO {

    @Schema(description = "The SignInDTO's username", example = "john_doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The SignInDTO username cannot be blank")
    private String username;

    @Schema(description = "The SignInDTO's password", example = "secure123pa55w0rd", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The SignInDTO password cannot be blank")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignInDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
