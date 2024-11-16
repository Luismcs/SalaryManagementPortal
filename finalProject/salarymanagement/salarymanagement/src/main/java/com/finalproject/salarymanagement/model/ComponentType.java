package com.finalproject.salarymanagement.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;

@Entity(name = "component_types")
@Audited
public class ComponentType extends AbstractEntity {

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "componentType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComponentTypeSubtype> componentTypeSubtypes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "componentType", cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE})
    private List<SalaryComponent> salaryComponents;

    @NotAudited
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ComponentTypeSubtype> getComponentTypeSubtypes() {
        return componentTypeSubtypes;
    }

    public void setComponentTypeSubtypes(List<ComponentTypeSubtype> componentTypeSubtypes) {
        this.componentTypeSubtypes = componentTypeSubtypes;
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
        return "ComponentType{" +
                "name='" + name + '\'' +
                ", componentTypeSubtypes=" + componentTypeSubtypes.stream().map(ComponentTypeSubtype::getId).toList() +
                '}';
    }
}
