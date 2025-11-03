package org.siri_hate.user_service.model.dto.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class MemberRegistrationRequest {

    @NotBlank(message = "Name should not be null")
    private String name;

    @NotBlank(message = "Email should not be null")
    @Email(message = "Must provide a valid email address")
    private String email;

    @NotBlank(message = "Phone should not be null")
    private String phone;

    @NotNull(message = "Birth day should not be null")
    private LocalDate birthday;

    @NotBlank(message = "Username id should not be null")
    private String username;

    @NotBlank(message = "Password id should not be null")
    @Size(min = 8, message = "Password must contain more than 8 characters")
    private String password;

    public MemberRegistrationRequest() {
    }

    public MemberRegistrationRequest(
            String name,
            String email,
            String phone,
            LocalDate birthday,
            String username,
            String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.username = username;
        this.password = password;
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
}
