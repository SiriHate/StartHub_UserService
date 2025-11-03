package org.siri_hate.user_service.model.dto.request.member;

public class MemberChangeProfileVisibilityRequest {

    private Boolean profileHiddenFlag;

    public MemberChangeProfileVisibilityRequest() {
    }

    public MemberChangeProfileVisibilityRequest(Boolean profileHiddenFlag) {
        this.profileHiddenFlag = profileHiddenFlag;
    }

    public boolean getProfileHiddenFlag() {
        return profileHiddenFlag;
    }

    public void setProfileHidden(Boolean profileHiddenFlag) {
        this.profileHiddenFlag = profileHiddenFlag;
    }
}
