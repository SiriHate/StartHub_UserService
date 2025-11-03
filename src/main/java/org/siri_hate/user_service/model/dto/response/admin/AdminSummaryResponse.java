package org.siri_hate.user_service.model.dto.response.admin;

public class AdminSummaryResponse {

    private Long id;
    private String username;

    public AdminSummaryResponse() {
    }

    public AdminSummaryResponse(Long id, String username) {
        this.id = id;
        this.username = username;
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
}
