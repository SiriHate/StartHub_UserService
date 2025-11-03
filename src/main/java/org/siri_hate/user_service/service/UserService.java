package org.siri_hate.user_service.service;

import org.siri_hate.user_service.model.dto.request.auth.LoginForm;
import org.siri_hate.user_service.model.dto.request.auth.YandexAuthRequest;
import org.siri_hate.user_service.model.dto.response.user.CurrentUserResponse;
import org.siri_hate.user_service.model.dto.response.user.UserLoginResponse;
import org.siri_hate.user_service.model.entity.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    UserLoginResponse userLogin(LoginForm loginForm);

    UserLoginResponse userLoginViaYandex(YandexAuthRequest request);

    User findMemberByUsername(String username);

    CurrentUserResponse getCurrentUser(Authentication authentication);

    void deleteUserByUsername(String username);
}
