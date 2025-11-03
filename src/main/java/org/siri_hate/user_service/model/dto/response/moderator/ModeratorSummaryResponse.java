package org.siri_hate.user_service.model.dto.response.moderator;

public class ModeratorSummaryResponse {

    private Long id;
    private String username;
    private String name;
    private Long employeeId;

    public ModeratorSummaryResponse() {
    }

    public ModeratorSummaryResponse(Long id, String username, String name, Long employeeId) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.employeeId = employeeId;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
