package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public class SignUpResponseDTO {

    @Schema(description = "The collaborator's id", example = "1", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private Long collaboratorId;

    @Schema(description = "The userCredentials's id", example = "1", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private Long userCredentialsId;

    @Schema(description = "The collaborator's username", example = "John Doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The username cannot be empty")
    private String username;

    private List<String> roles;

    @Schema(description = "The collaborator's full name", example = "John Doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The full name cannot be empty")
    private String fullName;

    @Schema(description = "The collaborator's gender", example = "M", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The gender cannot be empty")
    private String gender;

    @Schema(description = "The collaborator's birth date", example = "2003-09-23", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The birth date cannot be empty")
    @Past(message = "The birth date must be in the past")
    private LocalDate birthDate;

    @Schema(description = "The collaborator's nif", example = "123123123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The nif cannot be empty")
    private String nif;

    @Schema(description = "The collaborator's email", example = "john_doe@email.com", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The email cannot be empty")
    private String email;

    private List<AddressDTO> addresses;

    public Long getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Long collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public Long getUserCredentialsId() {
        return userCredentialsId;
    }

    public void setUserCredentialsId(Long userCredentialsId) {
        this.userCredentialsId = userCredentialsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "SignUpResponseDTO{" +
                "collaboratorId=" + collaboratorId +
                ", userCredentialsId=" + userCredentialsId +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
