package org.siri_hate.user_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.siri_hate.user_service.model.auth.YandexUserInfo;
import org.siri_hate.user_service.model.dto.request.auth.LoginForm;
import org.siri_hate.user_service.model.dto.request.auth.YandexAuthRequest;
import org.siri_hate.user_service.model.dto.response.user.CurrentUserResponse;
import org.siri_hate.user_service.model.dto.response.user.UserLoginResponse;
import org.siri_hate.user_service.model.entity.Member;
import org.siri_hate.user_service.model.entity.User;
import org.siri_hate.user_service.model.enums.AuthType;
import org.siri_hate.user_service.model.enums.UserRole;
import org.siri_hate.user_service.repository.MemberRepository;
import org.siri_hate.user_service.repository.UserRepository;
import org.siri_hate.user_service.security.JWTService;
import org.siri_hate.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MemberRepository memberRepository;
    private final WebClient webClient;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JWTService jwtService,
            MemberRepository memberRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
        this.webClient = WebClient.builder()
                .baseUrl("https://login.yandex.ru")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    private static Member getNewMember(YandexUserInfo yandexUserInfo) {
        Member newMember = new Member();
        newMember.setUsername(yandexUserInfo.getLogin());
        newMember.setName(yandexUserInfo.getDisplayName());
        newMember.setEmail(yandexUserInfo.getDefaultEmail());
        newMember.setPhone(
                yandexUserInfo.getDefaultPhone() != null ? yandexUserInfo.getDefaultPhone().getNumber()
                        : "");
        newMember.setRole(UserRole.MEMBER.name());
        newMember.setEnabled(true);
        newMember.setAuthType(AuthType.YANDEX);
        newMember.setProfileHiddenFlag(false);
        newMember.setBirthday(LocalDate.parse(yandexUserInfo.getBirthday()));
        newMember.setPassword("");
        return newMember;
    }

    @Override
    public UserLoginResponse userLogin(LoginForm loginForm) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginForm.getUsername(), loginForm.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginForm.getUsername(),
                    authentication.getAuthorities());
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            return new UserLoginResponse(loginForm.getUsername(), token, role);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @Override
    public UserLoginResponse userLoginViaYandex(YandexAuthRequest request) {
        YandexUserInfo yandexUserInfo = webClient.get()
                .uri("/info")
                .header("Authorization", "OAuth " + request.getToken())
                .retrieve()
                .bodyToMono(YandexUserInfo.class)
                .block();

        if (yandexUserInfo == null) {
            throw new UsernameNotFoundException("Failed to get user info from Yandex");
        }

        Member existingMember = memberRepository.findMemberByEmail(yandexUserInfo.getDefaultEmail());

        if (existingMember != null) {
            String token = jwtService.generateToken(existingMember.getUsername(),
                    existingMember.getAuthorities());
            return new UserLoginResponse(existingMember.getUsername(), token, existingMember.getRole());
        }

        Member newMember = getNewMember(yandexUserInfo);
        memberRepository.save(newMember);
        String token = jwtService.generateToken(newMember.getUsername(), newMember.getAuthorities());
        return new UserLoginResponse(newMember.getUsername(), token, newMember.getRole());
    }

    @Override
    public User findMemberByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User with username: " + username + " not found!");
        }
        return userOptional.get();
    }

    @Override
    public CurrentUserResponse getCurrentUser(Authentication authentication) {
        CurrentUserResponse currentUserResponse = new CurrentUserResponse();
        Optional<User> user = userRepository.findUserByUsername(authentication.getName());

        if (user.isPresent()) {
            User foundUser = user.get();
            currentUserResponse.setId(foundUser.getId());
            currentUserResponse.setUsername(foundUser.getUsername());
            currentUserResponse.setRole(foundUser.getRole());
        } else {
            throw new EntityNotFoundException(
                    "User not found with username: " + authentication.getName());
        }

        return currentUserResponse;
    }

    @Override
    public void deleteUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            return;
        }
        userRepository.delete(userOptional.get());
    }
}
