package org.siri_hate.user_service.model.enums;

public enum UserRole {
    MEMBER("MEMBER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");
    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
