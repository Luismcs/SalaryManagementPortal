package com.finalproject.portal.dto;

import com.finalproject.portal.enums.SalaryState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class FilteredExportRequestDTO {

    @Schema(description = "Filtered Export Request's start date", example = "2023-10-10", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The FilteredExportRequest's starting Date cannot be null")
    private LocalDate startDate;

    @Schema(description = "Filtered Export Request's ending date", example = "2023-10-12", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The FilteredExportRequest's ending Date cannot be null")
    private LocalDate endDate;

    @Schema(description = "Filtered Export Request's salary state", example = "ACTIVE", requiredMode =
            Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The FilteredExportRequest's salary State cannot be null")
    private SalaryState salaryState;

    @Schema(description = "Filtered Export Request's correlation id", example = "1", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private String correlationId;

    @Schema(description = "Filtered Export Request's email", example = "johndoe@email.com", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private String email;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SalaryState getSalaryState() {
        return salaryState;
    }

    public void setSalaryState(SalaryState salaryState) {
        this.salaryState = salaryState;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
