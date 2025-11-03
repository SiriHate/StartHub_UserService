package org.siri_hate.user_service.service;

import org.siri_hate.user_service.model.entity.Member;

public interface ConfirmationService {

    void sendRegistrationConfirmation(Member member);

    void sendChangePasswordConfirmation(Member member);

    String generateConfirmationToken();

    void checkConfirmationToken(String token);

    Long getUserIdByToken(String token);

    void deleteConfirmationTokenByTokenValue(String token);

    void findConfirmationTokenByTokenValue(String token);
}
