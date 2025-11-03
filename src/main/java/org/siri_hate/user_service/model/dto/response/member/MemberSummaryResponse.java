package org.siri_hate.user_service.model.dto.response.member;

public class MemberSummaryResponse {

    private Long id;
    private String username;
    private String name;
    private String email;
    private String avatarUrl;
    private String specialization;

    public MemberSummaryResponse() {
    }

    public MemberSummaryResponse(
            Long id, String username, String name, String email, String avatarUrl,
            String specialization) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.specialization = specialization;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
