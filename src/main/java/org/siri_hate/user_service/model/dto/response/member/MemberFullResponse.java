package org.siri_hate.user_service.model.dto.response.member;

import java.time.LocalDate;

public class MemberFullResponse {

    private Long id;
    private String username;
    private String avatarUrl;
    private String name;
    private String specialization;
    private String about;
    private String email;
    private String phone;
    private LocalDate birthday;
    private boolean profileHiddenFlag;

    public MemberFullResponse() {
    }

    public MemberFullResponse(
            Long id,
            String username,
            String avatarUrl,
            String name,
            String specialization,
            String about,
            String email,
            String phone,
            LocalDate birthday,
            boolean profileHiddenFlag) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.specialization = specialization;
        this.about = about;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.profileHiddenFlag = profileHiddenFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isProfileHiddenFlag() {
        return profileHiddenFlag;
    }

    public void setProfileHiddenFlag(boolean profileHiddenFlag) {
        this.profileHiddenFlag = profileHiddenFlag;
    }
}
