package com.finalproject.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UserCredentialsDTO extends AbstractDTO {

    @Schema(description = "The User Credentials username", example = "john_doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "The User Credentials's password", example = "abcpa55w0rd", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "The User Credentials's correlation id", example = "1", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private String correlationId;

    @Schema(description = "The User Credentials's roles", example = "ADMIN", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private List<RoleDTO> roles;

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

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
