package org.siri_hate.user_service.model.entity;

import jakarta.persistence.*;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "token_value")
    private String tokenValue;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String tokenType, String tokenValue, Member member) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
