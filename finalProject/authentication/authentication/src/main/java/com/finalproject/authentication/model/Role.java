package com.finalproject.authentication.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "roles")
@Audited
public class Role extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCredentialsRole> userCredentialsRoles = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<UserCredentialsRole> getUserCredentialsRoles() {
        return userCredentialsRoles;
    }

    public void setUserCredentialsRoles(Set<UserCredentialsRole> userCredentialsRoles) {
        this.userCredentialsRoles = userCredentialsRoles;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", userCredentialsRoles=" + userCredentialsRoles +
                '}';
    }
}
