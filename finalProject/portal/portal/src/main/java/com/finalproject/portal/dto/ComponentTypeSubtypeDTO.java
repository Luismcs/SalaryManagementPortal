package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class ComponentTypeSubtypeDTO extends AbstractDTO {

    @Schema(description = "ComponentTypeSubtype's ComponentType Id", example = "1", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The Component Type Subtype's ComponentType Id cannot be null")
    private Long componentTypeId;

    @Schema(description = "Component Type Subtype's name", example = "CoverFlex", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The Component Type Subtype's name cannot be null")
    private String name;

    public Long getComponentTypeId() {
        return componentTypeId;
    }

    public void setComponentTypeId(Long componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ComponentTypeSubtypeDTO{" +
                "name='" + name + '\'' +
                ", componentTypeId=" + componentTypeId +
                '}';
    }
}
