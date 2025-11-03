package org.siri_hate.user_service.model.dto.request.auth;

public class YandexAuthRequest {

    private String token;
    private String clientSecret;

    public YandexAuthRequest() {
    }

    public YandexAuthRequest(String token, String clientSecret) {
        this.token = token;
        this.clientSecret = clientSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
