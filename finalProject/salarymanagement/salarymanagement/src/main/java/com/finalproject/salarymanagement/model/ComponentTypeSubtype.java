package com.finalproject.salarymanagement.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;

@Entity(name = "component_types_subtypes")
@Audited
public class ComponentTypeSubtype extends AbstractEntity {

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_type_id")
    private ComponentType componentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "componentTypeSubtype", cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE})
    private List<SalaryComponent> salaryComponents;

    @NotAudited
    @Version
    @Column
    private Long version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
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
        return "ComponentTypeSubtype{" +
                "name='" + name + '\'' +
                ", componentTypeId=" + (componentType != null ? componentType.getId() : null) +
                '}';
    }
}
