package org.siri_hate.user_service.model.dto.request.member;

import org.siri_hate.user_service.model.entity.SpecialistSpecialization;

import java.time.LocalDate;

public class MemberFullRequest {

    private String username;
    private String password;
    private String role;
    private boolean isEnabled;
    private String avatarUrl;
    private String name;
    private SpecialistSpecialization specialization;
    private String about;
    private String email;
    private String phone;
    private LocalDate birthday;
    private boolean profileHiddenFlag;

    public MemberFullRequest() {
    }

    public MemberFullRequest(
            String username,
            String password,
            String role,
            boolean isEnabled,
            String avatarUrl,
            String name,
            SpecialistSpecialization specialization,
            String about,
            String email,
            String phone,
            LocalDate birthday,
            boolean profileHiddenFlag) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isEnabled = isEnabled;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.specialization = specialization;
        this.about = about;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.profileHiddenFlag = profileHiddenFlag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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

    public SpecialistSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecialistSpecialization specialization) {
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
