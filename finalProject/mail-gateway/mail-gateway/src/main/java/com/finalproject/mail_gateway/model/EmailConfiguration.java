package com.finalproject.mail_gateway.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;

@Entity
public class EmailConfiguration extends AbstractEntity{

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String subject;

    @Column
    private String destination;

    @Column
    private String body;

    @Version
    private Integer version;

    public EmailConfiguration(String name, String description, String subject, String destination, String body) {
        this.name = name;
        this.description = description;
        this.subject = subject;
        this.destination = destination;
        this.body = body;
    }

    public EmailConfiguration() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
