package org.siri_hate.user_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.siri_hate.user_service.model.dto.mapper.MemberMapper;
import org.siri_hate.user_service.model.dto.request.auth.ChangePasswordForm;
import org.siri_hate.user_service.model.dto.request.auth.RecoveryPasswordRequest;
import org.siri_hate.user_service.model.dto.request.member.*;
import org.siri_hate.user_service.model.dto.request.tokens.ChangePasswordTokenRequest;
import org.siri_hate.user_service.model.dto.response.member.MemberFullResponse;
import org.siri_hate.user_service.model.dto.response.member.MemberSummaryResponse;
import org.siri_hate.user_service.model.entity.Member;
import org.siri_hate.user_service.model.enums.AuthType;
import org.siri_hate.user_service.model.enums.UserRole;
import org.siri_hate.user_service.repository.MemberRepository;
import org.siri_hate.user_service.repository.adapters.MemberSpecification;
import org.siri_hate.user_service.service.ConfirmationService;
import org.siri_hate.user_service.service.FileService;
import org.siri_hate.user_service.service.KafkaProducerService;
import org.siri_hate.user_service.service.MemberService;
import org.siri_hate.user_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationService confirmationService;
    private final NotificationService notificationService;
    private final MemberMapper memberMapper;
    private final KafkaProducerService kafkaProducerService;
    private final FileService fileService;

    @Autowired
    private MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, @Lazy ConfirmationService confirmationService, NotificationService notificationService, MemberMapper memberMapper, KafkaProducerService kafkaProducerService, FileService fileService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationService = confirmationService;
        this.notificationService = notificationService;
        this.memberMapper = memberMapper;
        this.kafkaProducerService = kafkaProducerService;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public void memberRegistration(MemberRegistrationRequest member) {
        Member memberEntity = memberMapper.toMemberFromRegistration(member);
        if (memberRepository.findMemberByUsername(memberEntity.getUsername()) != null) {
            throw new RuntimeException("Member with provided email or phone already exists!");
        }
        memberEntity.setPassword(passwordEncoder.encode(memberEntity.getPassword()));
        memberEntity.setRole(UserRole.MEMBER);
        memberEntity.setAuthType(AuthType.PASSWORD);
        memberEntity.setProfileHiddenFlag(false);
        memberRepository.save(memberEntity);
        confirmationService.sendRegistrationConfirmation(memberEntity);
    }

    @Override
    public void activateMemberAccount(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isEmpty()) {
            throw new EntityNotFoundException("Member with id: " + id + " not found!");
        }
        Member member = memberOptional.get();
        member.setEnabled(true);
        memberRepository.save(member);
        String memberName = member.getName();
        String memberEmail = member.getEmail();
        notificationService.sendSuccessfulRegistrationNotification(memberName, memberEmail);
    }

    @Override
    public void memberPasswordRecoveryRequest(RecoveryPasswordRequest recoveryPasswordRequest) {
        String email = recoveryPasswordRequest.getEmail();
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new EntityNotFoundException("Member with email: " + email + " not found!");
        }
        confirmationService.sendChangePasswordConfirmation(member);
    }

    @Override
    public void memberPasswordRecoveryConfirmation(ChangePasswordTokenRequest changePasswordTokenRequest) {
        String token = changePasswordTokenRequest.getToken();
        String newPassword = changePasswordTokenRequest.getNewPassword();
        Long userId = confirmationService.getUserIdByToken(token);
        Optional<Member> memberOptional = memberRepository.findById(userId);
        if (memberOptional.isEmpty()) {
            throw new EntityNotFoundException("Member with id: " + userId + " not found!");
        }
        Member member = memberOptional.get();
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
        confirmationService.deleteConfirmationTokenByTokenValue(token);
        String memberName = member.getName();
        String memberEmail = member.getEmail();
        notificationService.sendChangedPasswordNotification(memberName, memberEmail);
    }

    @Override
    @Transactional
    public void memberPasswordChange(String username, ChangePasswordForm changePasswordForm) {
        String currentPassword = changePasswordForm.getCurrentPassword();
        String newPassword = changePasswordForm.getNewPassword();
        Member member = memberRepository.findMemberByUsername(username);
        if (Objects.isNull(member)) {
            throw new EntityNotFoundException("Member with id: " + username + " not found!");
        }
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new RuntimeException("Entered password does not match the current one");
        }
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    @Override
    public Page<MemberSummaryResponse> getAllMembers(String username, String specialization, Boolean profileHiddenFlag, Pageable pageable) {
        Specification<Member> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (username != null && !username.isEmpty()) {
            spec = spec.and(MemberSpecification.usernameStartsWithIgnoreCase(username));
        }

        if (specialization != null && !specialization.isEmpty()) {
            spec = spec.and(MemberSpecification.hasSpecialization(specialization));
        }

        if (profileHiddenFlag != null) {
            if (profileHiddenFlag) {
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("profileHiddenFlag")));
            } else {
                spec = spec.and(MemberSpecification.profileIsNotHidden());
            }
        }

        Page<Member> members = memberRepository.findAll(spec, pageable);
        return members.map(this::toMemberSummaryResponseWithAvatar);
    }

    @Override
    public MemberFullResponse getMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isEmpty()) {
            throw new EntityNotFoundException("Member with id: " + id + " not found!");
        }
        return toMemberFullResponseWithAvatar(member.get());
    }

    @Override
    public MemberFullResponse getMemberByUsername(String username) {
        Member member = memberRepository.findMemberByUsername(username);
        if (member == null) {
            throw new EntityNotFoundException("Member with username: " + username + " not found!");
        }
        return toMemberFullResponseWithAvatar(member);
    }

    @Override
    @Transactional
    public MemberFullResponse memberUpdate(Long id, MemberFullRequest member) {
        Member currentMember = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member with id: " + id + " not found!"));
        Member updatedMember = memberMapper.memberUpdateFullData(member, currentMember);
        memberRepository.save(updatedMember);
        return toMemberFullResponseWithAvatar(updatedMember);
    }

    @Override
    @Transactional
    public void deleteMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member with id: " + id + " not found!"));

        String username = member.getUsername();
        memberRepository.delete(member);
        kafkaProducerService.sendUserDeletionMessage(username);
    }

    @Override
    @Transactional
    public void deleteMemberByUsername(String username) {
        Member member = memberRepository.findMemberByUsername(username);
        if (Objects.isNull(member)) {
            throw new EntityNotFoundException("Member with username: " + username + " not found!");
        }
        memberRepository.delete(member);
        String name = member.getName();
        String email = member.getEmail();
        notificationService.sendDeletedAccountNotification(name, email);
        kafkaProducerService.sendUserDeletionMessage(username);
    }

    @Override
    @Transactional
    public void memberChangeAvatar(String username, MultipartFile file) {
        Member member = memberRepository.findMemberByUsername(username);
        if (Objects.isNull(member)) {
            throw new EntityNotFoundException("Member with username: " + username + " not found!");
        }
        String avatarKey = fileService.uploadAvatar(username, file);
        member.setAvatarUrl(avatarKey);
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public MemberFullResponse memberChangePersonalInfo(String username, MemberProfileDataRequest profileDataRequest) {
        Member member = memberRepository.findMemberByUsername(username);
        if (member == null) {
            throw new EntityNotFoundException("Member with username: " + username + " not found!");
        }
        Member updatedMember = memberMapper.memberUpdateProfileData(profileDataRequest, member);
        memberRepository.save(updatedMember);
        return toMemberFullResponseWithAvatar(updatedMember);
    }

    @Override
    @Transactional
    public MemberFullResponse changeMemberProfileVisibility(MemberChangeProfileVisibilityRequest request, String username) {
        Member member = memberRepository.findMemberByUsername(username);
        if (Objects.isNull(member)) {
            throw new EntityNotFoundException("Member with username: " + username + " not found!");
        }
        member.setProfileHiddenFlag(request.getProfileHiddenFlag());
        memberRepository.save(member);
        return toMemberFullResponseWithAvatar(member);
    }

    @Override
    public Page<MemberSummaryResponse> searchMembersByName(String name, Pageable pageable) {
        Specification<Member> spec = MemberSpecification.nameStartsWithIgnoreCase(name);
        Page<Member> members = memberRepository.findAll(spec, pageable);
        if (members.isEmpty()) {
            throw new RuntimeException("No members found");
        }
        return members.map(this::toMemberSummaryResponseWithAvatar);
    }

    private MemberFullResponse toMemberFullResponseWithAvatar(Member member) {
        MemberFullResponse response = memberMapper.toMemberFullResponse(member);
        response.setAvatarUrl(fileService.getAvatarUrl(member.getAvatarUrl()));
        return response;
    }

    private MemberSummaryResponse toMemberSummaryResponseWithAvatar(Member member) {
        MemberSummaryResponse response = memberMapper.toMemberSummaryResponse(member);
        response.setAvatarUrl(fileService.getAvatarUrl(member.getAvatarUrl()));
        return response;
    }
}
