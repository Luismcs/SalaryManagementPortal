package com.finalproject.portal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class SalaryComponentDTO extends AbstractDTO{

    @Schema(description = "Salary Component's Component Type Id", example = "1", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The Salary Component's Component Type Id cannot be null")
    private Long componentTypeId;

    @Schema(description = "Salary Component's Component Type Subtype Id", example = "2", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The Salary Component's Component Type Subtype Id cannot be null")
    private Long componentTypeSubtypeId;

    @Schema(description = "Salary Component's Salary Id", example = "2", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The Salary Component's Salary Id cannot be null")
    private Long salaryId;

    @Schema(description = "Salary Component's value", example = "123", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The Salary Component's value cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "The Salary Component's value must be at least 0.01")
    private Double value;

    @Schema(description = "Salary Component's present On Receipt", example = "true", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The Salary Component's present On Receipt cannot be null")
    private Boolean presentOnReceipt;

    public Long getComponentTypeId() {
        return componentTypeId;
    }

    public void setComponentTypeId(Long componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    public Long getComponentTypeSubtypeId() {
        return componentTypeSubtypeId;
    }

    public void setComponentTypeSubtypeId(Long componentTypeSubtypeId) {
        this.componentTypeSubtypeId = componentTypeSubtypeId;
    }

    public @NotNull(message = "The Salary Component's Salary Id cannot be null") Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(@NotNull(message = "The Salary Component's Salary Id cannot be null") Long salaryId) {
        this.salaryId = salaryId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getPresentOnReceipt() {
        return presentOnReceipt;
    }

    public void setPresentOnReceipt(Boolean presentOnReceipt) {
        this.presentOnReceipt = presentOnReceipt;
    }

    @Override
    public String toString() {
        return "SalaryComponentDTO{" +
                ", componentTypeId=" + componentTypeId +
                ", componentTypeSubtypeId=" + componentTypeSubtypeId +
                ", value=" + value +
                ", presentOnReceipt=" + presentOnReceipt +
                '}';
    }
}
