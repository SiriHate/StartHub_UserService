package org.siri_hate.user_service.model.dto.response.user;

import org.siri_hate.user_service.model.enums.UserRole;

public class UserLoginResponse {

    private String username;
    private String token;
    private UserRole role;

    public UserLoginResponse() {
    }

    public UserLoginResponse(String username, String token, UserRole role) {
        this.username = username;
        this.token = token;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

