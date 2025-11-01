package com.finalproject.portal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UserCredentialsDTO extends AbstractDTO {

    @Schema(description = "The User Credentials username", example = "john_doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "The User Credentials password", example = "abcpa55w0rd", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "The User Credentials correlation id", example = "1", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private String correlationId;

    @Schema(description = "The User Credentials roles", example = "ADMIN", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private List<RoleDTO> roles;

    public UserCredentialsDTO(String username, String password, String correlationId, List<RoleDTO> roles) {
        this.username = username;
        this.password = password;
        this.correlationId = correlationId;
        this.roles = roles;
    }

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

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserCredentialsDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", roles=" + roles +
                '}';
    }
}
