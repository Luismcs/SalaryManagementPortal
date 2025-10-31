package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ComponentTypeDTO extends AbstractDTO {

    @Schema(description = "The Component Type's name", example = "CoverFlex", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The component type's name cannot be blank")
    private String name;

    @Schema(description = "The Component Type's Component Type Subtype list", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private List<ComponentTypeSubtypeDTO> componentTypeSubtypes;

    public List<ComponentTypeSubtypeDTO> getComponentTypeSubtypes() {
        return componentTypeSubtypes;
    }

    public void setComponentTypeSubtypes(List<ComponentTypeSubtypeDTO> componentTypeSubtypes) {
        this.componentTypeSubtypes = componentTypeSubtypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ComponentTypeDTO{" +
                "name='" + name + '\'' +
                ", componentTypeSubtypes=" + componentTypeSubtypes +
                '}';
    }
}
