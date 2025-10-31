package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComponentTypeSubtypeDTO extends AbstractDTO {

    @Schema(description = "The Component Type Subtype's Component Type Id", example = "1", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The component type subtype's component type id cannot be null")
    private Long componentTypeId;

    @Schema(description = "The Component Type Subtype's name", example = "CoverFlex", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The component type subtype's name cannot be blank")
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
