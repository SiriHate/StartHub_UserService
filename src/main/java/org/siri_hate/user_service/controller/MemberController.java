package org.siri_hate.user_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.siri_hate.user_service.model.dto.request.auth.ChangePasswordForm;
import org.siri_hate.user_service.model.dto.request.auth.RecoveryPasswordRequest;
import org.siri_hate.user_service.model.dto.request.member.*;
import org.siri_hate.user_service.model.dto.request.tokens.ChangePasswordTokenRequest;
import org.siri_hate.user_service.model.dto.response.member.MemberFullResponse;
import org.siri_hate.user_service.model.dto.response.member.MemberSummaryResponse;
import org.siri_hate.user_service.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/user_service/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<String> memberRegistration(
            @RequestBody @Valid MemberRegistrationRequest member) {
        memberService.memberRegistration(member);
        return new ResponseEntity<>("Successful registration", HttpStatus.CREATED);
    }

    @PostMapping("/password_recovery_requests")
    public ResponseEntity<String> memberPasswordRecoveryRequest(
            @RequestBody RecoveryPasswordRequest recoveryPasswordRequest) {
        memberService.memberPasswordRecoveryRequest(recoveryPasswordRequest);
        return new ResponseEntity<>(
                "Request to change the password has been successfully sent", HttpStatus.OK);
    }

    @PatchMapping("/password_recovery_confirmations")
    public ResponseEntity<String> memberPasswordRecoveryConfirm(
            @RequestBody ChangePasswordTokenRequest changePasswordTokenRequest) {
        memberService.memberPasswordRecoveryConfirmation(changePasswordTokenRequest);
        return new ResponseEntity<>("Password has been successfully changed", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<MemberSummaryResponse>> getAllMembers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) Boolean profileHiddenFlag,
            @PageableDefault() Pageable pageable) {
        Page<MemberSummaryResponse> members = memberService.getAllMembers(username, specialization,
                profileHiddenFlag, pageable);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberFullResponse> getMemberById(
            @Positive(message = "ID should be greater than zero") @PathVariable("id") Long id) {
        MemberFullResponse member = memberService.getMemberById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<MemberFullResponse> getMemberByUsername(
            @PathVariable("username") String username) {
        MemberFullResponse member = memberService.getMemberByUsername(username);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemberFullResponse> memberUpdate(
            @Positive(message = "ID should be greater than zero") @PathVariable("id") Long id,
            @Valid MemberFullRequest memberFullRequest) {
        MemberFullResponse updatedMember = memberService.memberUpdate(id, memberFullRequest);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMember(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "username", required = false) String username) {
        if (id != null) {
            memberService.deleteMemberById(id);
        } else if (username != null) {
            memberService.deleteMemberByUsername(username);
        } else {
            return ResponseEntity.badRequest().body("Either 'id' or 'username' must be provided.");
        }
        return new ResponseEntity<>("Successful delete", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/me/personal_info")
    public ResponseEntity<MemberFullResponse> getMyPersonInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        MemberFullResponse member = memberService.getMemberByUsername(username);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMemberByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberService.deleteMemberByUsername(username);
        return new ResponseEntity<>("Successful delete", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/me/password")
    public ResponseEntity<String> changeMemberPasswordByAuth(
            @RequestBody @Valid ChangePasswordForm changePasswordForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberService.memberPasswordChange(username, changePasswordForm);
        return new ResponseEntity<>("Password has been successfully changed", HttpStatus.OK);
    }

    @PatchMapping("/me/avatar")
    public ResponseEntity<String> changeMemberAvatar(
            @RequestBody @Valid MemberChangeAvatarRequest avatar) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberService.memberChangeAvatar(username, avatar);
        return new ResponseEntity<>("Avatar has been successfully changed", HttpStatus.OK);
    }

    @PatchMapping("/me/personal_info")
    public ResponseEntity<MemberFullResponse> changePersonalInfoByAuth(
            @RequestBody @Valid MemberProfileDataRequest profileData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        MemberFullResponse updatedMember =
                memberService.memberChangePersonalInfo(username, profileData);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @PatchMapping("/me/profile_hidden_flag")
    public ResponseEntity<String> changeMemberProfileVisibility(
            @Valid @RequestBody MemberChangeProfileVisibilityRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        memberService.changeMemberProfileVisibility(request, username);
        return new ResponseEntity<>("Profile visibility has been successfully changed", HttpStatus.OK);
    }
}
