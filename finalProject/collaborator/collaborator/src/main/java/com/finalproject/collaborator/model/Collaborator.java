package com.finalproject.collaborator.model;

import com.finalproject.collaborator.enums.Gender;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "collaborators")
@Audited
public class Collaborator extends AbstractEntity {

    @Column
    private String fullName;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate birthDate;

    @Column
    private String nif;

    @Column
    private String email;

    @OneToMany(mappedBy = "collaborator", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birth_date) {
        this.birthDate = birth_date;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Collaborator{" +
                "fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
