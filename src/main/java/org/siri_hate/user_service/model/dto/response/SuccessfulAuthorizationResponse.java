package org.siri_hate.user_service.model.dto.response;

public class SuccessfulAuthorizationResponse {

    private String message;
    private String token;

    public SuccessfulAuthorizationResponse() {
    }

    public SuccessfulAuthorizationResponse(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
