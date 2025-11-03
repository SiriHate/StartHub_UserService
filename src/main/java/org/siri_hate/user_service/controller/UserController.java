package org.siri_hate.user_service.controller;

import jakarta.validation.Valid;
import org.siri_hate.user_service.model.dto.request.auth.LoginForm;
import org.siri_hate.user_service.model.dto.request.auth.YandexAuthRequest;
import org.siri_hate.user_service.model.dto.response.user.CurrentUserResponse;
import org.siri_hate.user_service.model.dto.response.user.UserLoginResponse;
import org.siri_hate.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/user_service/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody @Valid LoginForm loginForm) {
        UserLoginResponse response = userService.userLogin(loginForm);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/yandex")
    public ResponseEntity<UserLoginResponse> userLoginViaYandex(
            @RequestBody @Valid YandexAuthRequest request) {
        UserLoginResponse response = userService.userLoginViaYandex(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getCurrentUser(authentication));
    }
}
