package com.finalproject.salarymanagement.model;

import com.finalproject.salarymanagement.enums.SalaryState;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;
import java.util.List;

@Entity
@Audited
public class Salary extends AbstractEntity {

    @Column
    private String correlationId;

    @Column
    private LocalDate acceptanceDate;

    @Column
    private LocalDate implementationDate;

    @Column
    @Enumerated(EnumType.STRING)
    private SalaryState salaryState = SalaryState.INACTIVE;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalaryComponent> salaryComponents;

    @NotAudited
    @Version
    @Column
    private Long version;

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

    public SalaryState getSalaryState() {
        return salaryState;
    }

    public void setSalaryState(SalaryState salaryState) {
        this.salaryState = salaryState;
    }

    public List<SalaryComponent> getSalaryComponents() {
        return salaryComponents;
    }

    public void setSalaryComponents(List<SalaryComponent> salaryComponents) {
        this.salaryComponents = salaryComponents;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "correlationId='" + correlationId + '\'' +
                ", acceptanceDate=" + acceptanceDate +
                ", implementationDate=" + implementationDate +
                ", salaryState=" + salaryState +
                ", salaryComponents=" + salaryComponents +
                '}';
    }
}
