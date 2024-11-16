package com.finalproject.authentication.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "user_credentials")
@Audited
public class UserCredentials extends AbstractEntity {

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String correlationId;

    @OneToMany(mappedBy = "userCredentials", cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE}, orphanRemoval = true)
    private Set<UserCredentialsRole> userCredentialsRoles = new HashSet<>();

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCredentials", cascade = {CascadeType.ALL,
            CascadeType.REMOVE}, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;

    @Version
    @Column()
    private Long version;

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
