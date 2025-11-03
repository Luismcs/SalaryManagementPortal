package com.finalproject.collaborator.config;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    private final ThreadLocal<String> currentUser;

    public UserContext() {
        this.currentUser = new ThreadLocal<>();
    }

    public void setCurrentUser(String username) {
        this.currentUser.set(username);
    }

    public String getCurrentUser() {
        return this.currentUser.get();
    }

    public void clear() {
        this.currentUser.remove();
    }

}
