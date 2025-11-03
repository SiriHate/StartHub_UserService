package org.siri_hate.user_service.model.dto.request.member;

import jakarta.validation.constraints.NotBlank;

public class MemberChangeAvatarRequest {

    @NotBlank(message = "Avatar url must not be null")
    String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
