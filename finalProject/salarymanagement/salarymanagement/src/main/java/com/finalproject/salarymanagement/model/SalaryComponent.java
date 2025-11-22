package com.finalproject.salarymanagement.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "salary_components")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class SalaryComponent extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_type_id")
    private ComponentType componentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_type_subtype_id")
    private ComponentTypeSubtype componentTypeSubtype;

    @Column
    private double value;

    @Column
    private boolean presentOnReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_id")
    private Salary salary;

    @NotAudited
    @Version
    @Column
    private Long version;

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public ComponentTypeSubtype getComponentTypeSubtype() {
        return componentTypeSubtype;
    }

    public void setComponentTypeSubtype(ComponentTypeSubtype componentTypeSubtype) {
        this.componentTypeSubtype = componentTypeSubtype;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isPresentOnReceipt() {
        return presentOnReceipt;
    }

    public void setPresentOnReceipt(boolean presentOnReceipt) {
        this.presentOnReceipt = presentOnReceipt;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SalaryComponent{" +
                "id=" + this.getId() +
                "value=" + this.value+
                ", componentTypeId=" + (componentType != null ? componentType.getId() : null) +
                ", componentSubtypeId=" + (this.getComponentType() != null ? this.getComponentTypeSubtype() : null) +
                '}';
    }
}
