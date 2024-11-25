package com.finalproject.salarymanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;
import java.util.List;

public class SalaryDTO extends AbstractDTO {

    @Schema(description = "Salary's correlationId", example = "1", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private String correlationId;

    @Schema(description = "Salary's acceptance Date", example = "2023-10-10", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    @Future(message = "Salary's acceptance Date must be after today")
    private LocalDate acceptanceDate;

    @Schema(description = "Salary's implementation Date", example = "2023-10-10", requiredMode =
            Schema.RequiredMode.REQUIRED)
    //@Future(message = "Salary's implementation Date must be after today")
    private LocalDate implementationDate;

    @Schema(description = "Salary's status", example = "ACTIVE", requiredMode =
            Schema.RequiredMode.NOT_REQUIRED)
    private String salaryState;

    @Schema(description = "Salary's salary Components", example = "CoverFlex", requiredMode =
            Schema.RequiredMode.REQUIRED)
    private List<SalaryComponentDTO> salaryComponents;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public LocalDate getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(LocalDate acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public LocalDate getImplementationDate() {
        return implementationDate;
    }

    public void setImplementationDate(LocalDate implementationDate) {
        this.implementationDate = implementationDate;
    }

    public String getSalaryState() {
        return salaryState;
    }

    public void setSalaryState(String salaryState) {
        this.salaryState = salaryState;
    }

    public List<SalaryComponentDTO> getSalaryComponents() {
        return salaryComponents;
    }

    public void setSalaryComponents(List<SalaryComponentDTO> salaryComponents) {
        this.salaryComponents = salaryComponents;
    }

    @Override
    public String toString() {
        return "SalaryDTO{" +
                "correlationId='" + correlationId + '\'' +
                ", implementationDate=" + implementationDate +
                ", salaryComponents=" + salaryComponents +
                '}';
    }
}
