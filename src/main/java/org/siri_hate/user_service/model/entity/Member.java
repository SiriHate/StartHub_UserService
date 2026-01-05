package org.siri_hate.user_service.model.entity;

import jakarta.persistence.*;
import org.siri_hate.user_service.model.enums.AuthType;
import org.siri_hate.user_service.model.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "members")
@PrimaryKeyJoinColumn(name = "user_id")
public class Member extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private SpecialistSpecialization specialization;

    private String about;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(name = "is_hidden", nullable = false)
    private Boolean profileHiddenFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type", nullable = false)
    private AuthType authType;

    public Member() {}

    public Member(
            String username,
            String password,
            UserRole role,
            Boolean isEnabled,
            String avatarUrl,
            String name,
            SpecialistSpecialization specialization,
            String about,
            String email,
            String phone,
            LocalDate birthday,
            Boolean profileHiddenFlag,
            AuthType authType
    ) {
        super(username, password, role, isEnabled);
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.specialization = specialization;
        this.about = about;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.profileHiddenFlag = profileHiddenFlag;
        this.authType = authType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getProfileHiddenFlag() {
        return profileHiddenFlag;
    }

    public void setProfileHiddenFlag(Boolean profileHiddenFlag) {
        this.profileHiddenFlag = profileHiddenFlag;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
