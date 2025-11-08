package com.finalproject.authentication.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "user_credentials")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String correlationId;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @LastModifiedBy
    @Column
    private String lastModifiedBy;

    @LastModifiedDate
    @Column
    private Date lastModifiedDate;

    @Version
    @Column()
    private Long version;

    @OneToMany(mappedBy = "userCredentials", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCredentialsRole> userCredentialsRoles = new HashSet<>();

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCredentials", cascade = {CascadeType.ALL,
            CascadeType.REMOVE}, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<UserCredentialsRole> getUserCredentialsRoles() {
        return userCredentialsRoles;
    }

    public void setUserCredentialsRoles(Set<UserCredentialsRole> userCredentialsRoles) {
        this.userCredentialsRoles = userCredentialsRoles;
    }

    public List<RefreshToken> getRefreshTokens() {
        return refreshTokens;
    }

    public void setRefreshTokens(List<RefreshToken> refreshTokens) {
        this.refreshTokens = refreshTokens;
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", userCredentialsRoles=" + userCredentialsRoles +
                '}';
    }
}
