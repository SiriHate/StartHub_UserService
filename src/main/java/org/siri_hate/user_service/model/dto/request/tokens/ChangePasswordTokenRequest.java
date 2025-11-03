package org.siri_hate.user_service.model.dto.request.tokens;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordTokenRequest {

    @NotBlank(message = "Token must not be null")
    String token;

    @NotBlank(message = "New password must not be null")
    @Size(min = 8, message = "New password must contain more than 8 characters")
    String newPassword;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
