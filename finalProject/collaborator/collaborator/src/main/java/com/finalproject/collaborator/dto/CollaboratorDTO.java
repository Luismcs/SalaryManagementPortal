package com.finalproject.collaborator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class CollaboratorDTO extends AbstractDTO {

    @Schema(description = "The Collaborator's id", example = "1", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(description = "The Collaborator's full name", example = "John Doe", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The collaborator's full name cannot be empty")
    private String fullName;

    @Schema(description = "The Collaborator's gender", example = "M", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The collaborator's gender cannot be empty")
    private String gender;

    @Schema(description = "The Collaborator's birth date", example = "2003-09-23", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The collaborator's birth date cannot be empty")
    private LocalDate birthDate;

    @Schema(description = "The Collaborator's nif", example = "123123123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The collaborator's nif cannot be empty")
    @Size(min = 9, max = 9, message = "The nif must contain exactly 9 digits")
    private String nif;

    @Schema(description = "The Collaborator's email", example = "john_doe@email.com", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The collaborator's email cannot be empty")
    @Email(message = "The collaborator's email must be valid")
    private String email;

    @Schema(description = "The Collaborator's addresses", example = "john_doe@email.com", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The address cannot be empty")
    private List<AddressDTO> addresses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "CollaboratorDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
