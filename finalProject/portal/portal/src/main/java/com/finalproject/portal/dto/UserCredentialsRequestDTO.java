package com.finalproject.portal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UserCredentialsRequestDTO extends AbstractDTO{

    @Schema(description = "The User Credentials username", example = "john_doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "The User Credentials's password", example = "abcpa55w0rd", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "The User Credentials's correlation id", example = "1", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private String correlationId;

    @Schema(description = "The User Credentials's roles", example = "ADMIN", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private List<Long> roles;

    @Schema(description = "Object's current version", example = "1")
    private Long version;

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

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
