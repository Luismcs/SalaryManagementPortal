package com.finalproject.collaborator.model;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.envers.Audited;

@Entity(name = "addresses")
@Audited
public class Address extends AbstractEntity {

    @Column
    private String street;

    @Column
    private String postalCode;

    @Column
    private String city;

    @Column
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaborator_id")
    private Collaborator collaborator;

    public Address() {

    }

    public Address(String street, String postalCode, String city, String country, Collaborator collaborator) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.collaborator = collaborator;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", collaborator=" + collaborator +
                '}';
    }
}
