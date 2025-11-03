package org.siri_hate.user_service.model.dto.request.moderator;

public class ModeratorFullRequest {

    private String username;
    private String password;
    private String name;
    private Long employeeId;

    public ModeratorFullRequest() {
    }

    public ModeratorFullRequest(String username, String password, String name, Long employeeId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.employeeId = employeeId;
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
