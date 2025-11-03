package org.siri_hate.user_service.model.dto.request.tokens;

import jakarta.validation.constraints.NotBlank;

public class RegistrationTokenRequest {

    @NotBlank(message = "Token must not be null")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
