package org.siri_hate.user_service.model.dto.request.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.siri_hate.user_service.model.entity.SpecialistSpecialization;

import java.time.LocalDate;

public class MemberProfileDataRequest {

    @NotBlank(message = "Name must not be null")
    private String name;

    @NotBlank(message = "Phone must not be null")
    private String phone;

    @NotBlank(message = "Email must not be null")
    private String email;

    @NotNull(message = "Birthday must not be null")
    private LocalDate birthday;

    private String about;
    private SpecialistSpecialization specialization;

    public MemberProfileDataRequest() {
    }

    public MemberProfileDataRequest(
            String name,
            String phone,
            String email,
            LocalDate birthday,
            String about,
            SpecialistSpecialization specialization) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.about = about;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public SpecialistSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecialistSpecialization specialization) {
        this.specialization = specialization;
    }
}
