package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public class SignUpRequestDTO {

    @Schema(description = "The collaborator's id", example = "1", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(description = "The collaborator's username", example = "John Doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The collaborator's username cannot be blank")
    private String username;

    @Schema(description = "The collaborator's password", example = "abcpa55w0rd", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The collaborator's password cannot be blank")
    private String password;

    private List<RoleDTO> roles;

    @Schema(description = "The collaborator's full name", example = "John Doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The collaborator's full name cannot be blank")
    private String fullName;

    @Schema(description = "The collaborator's gender", example = "M", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The collaborator's gender cannot be blank")
    private String gender;

    @Schema(description = "The collaborator's birth date", example = "2003-09-23", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The collaborator's birth date cannot be empty")
    @Past(message = "The collaborator's birth date must be in the past")
    private LocalDate birthDate;

    @Schema(description = "The collaborator's nif", example = "123123123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The collaborator's nif cannot be blank")
    private String nif;

    @Schema(description = "The collaborator's email", example = "john_doe@email.com", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The collaborator email cannot be blank")
    private String email;

    private List<AddressDTO> addresses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
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
        return "SignUpRequestDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
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
