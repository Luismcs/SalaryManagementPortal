package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class AddressDTO extends AbstractDTO {

    @Schema(description = "The address's street", example = "123 Main St", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The street cannot be empty")
    private String street;

    @Schema(description = "The address's postalCode", example = "62701", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The postal-code cannot be empty")
    private String postalCode;

    @Schema(description = "The address's city", example = "Springfield", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The city cannot be empty")
    private String city;

    @Schema(description = "The address's country", example = "USA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The count cannot be empty")
    private String country;

    @Schema(description = "The address's collaborator ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The userAccountId cannot be empty")
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
