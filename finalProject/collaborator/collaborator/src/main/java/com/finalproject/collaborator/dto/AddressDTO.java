package com.finalproject.collaborator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressDTO extends AbstractDTO {

    @Schema(description = "The Address's street", example = "123 Main St", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The address's street cannot be blank")
    private String street;

    @Schema(description = "The Address's postalCode", example = "62701", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The address's postal-code cannot be blank")
    private String postalCode;

    @Schema(description = "The Address's city", example = "Springfield", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The address's city cannot be blank")
    private String city;

    @Schema(description = "The Address's country", example = "USA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The address's country cannot be blank")
    private String country;

    @Schema(description = "The Address's collaborator id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The address's collaboratorId cannot be empty")
    private Long collaboratorId;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Long collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", collaboratorId=" + collaboratorId +
                '}';
    }
}
