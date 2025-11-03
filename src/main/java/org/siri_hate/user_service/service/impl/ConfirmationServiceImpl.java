package org.siri_hate.user_service.service.impl;

import jakarta.transaction.Transactional;
import org.hibernate.StaleObjectStateException;
import org.siri_hate.user_service.service.KafkaProducerService;
import org.siri_hate.user_service.model.messages.ConfirmationMessage;
import org.siri_hate.user_service.model.entity.ConfirmationToken;
import org.siri_hate.user_service.model.entity.Member;
import org.siri_hate.user_service.model.enums.ConfirmationMessageType;
import org.siri_hate.user_service.model.enums.TokenType;
import org.siri_hate.user_service.repository.ConfirmationTokenRepository;
import org.siri_hate.user_service.service.ConfirmationService;
import org.siri_hate.user_service.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.ConcurrentModificationException;
import java.util.Optional;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MemberService memberService;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    private ConfirmationServiceImpl(
            ConfirmationTokenRepository confirmationTokenRepository,
            MemberService memberService,
            KafkaProducerService kafkaProducerService) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.memberService = memberService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public String generateConfirmationToken() {
        BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom(32);
        byte[] key = keyGenerator.generateKey();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(key);
    }

    @Override
    public void sendRegistrationConfirmation(Member member) {
        ConfirmationMessageType messageType = ConfirmationMessageType.REGISTRATION_CONFIRMATION;
        String tokenValue = generateConfirmationToken();
        String tokenType = TokenType.CONFIRM_REGISTRATION.getValue();
        ConfirmationToken confirmationToken = new ConfirmationToken(tokenType, tokenValue, member);
        confirmationTokenRepository.save(confirmationToken);
        String confirmationTokenValue = confirmationToken.getTokenValue();
        ConfirmationMessage confirmationMessage =
                new ConfirmationMessage(
                        messageType, member.getName(), member.getEmail(), confirmationTokenValue);
        kafkaProducerService.sendConfirmationToken(confirmationMessage);
    }

    @Override
    public void sendChangePasswordConfirmation(Member member) {
        ConfirmationMessageType messageType = ConfirmationMessageType.CHANGE_PASSWORD_CONFIRMATION;
        String tokenValue = generateConfirmationToken();
        String tokenType = TokenType.CONFIRM_CHANGE_PASSWORD.getValue();
        ConfirmationToken confirmationToken = new ConfirmationToken(tokenType, tokenValue, member);
        confirmationTokenRepository.save(confirmationToken);
        String confirmationTokenValue = confirmationToken.getTokenValue();
        ConfirmationMessage confirmationMessage =
                new ConfirmationMessage(
                        messageType, member.getName(), member.getEmail(), confirmationTokenValue);
        kafkaProducerService.sendConfirmationToken(confirmationMessage);
    }

    @Override
    @Transactional
    public void checkConfirmationToken(String token) {

        try {
            Optional<ConfirmationToken> foundToken =
                    confirmationTokenRepository.findConfirmationTokenByTokenValue(token);
            if (foundToken.isPresent()) {
                Long userId = foundToken.get().getMember().getId();
                memberService.activateMemberAccount(userId);
                confirmationTokenRepository.delete(foundToken.get());
            } else {
                throw new RuntimeException("Required confirmation token was not found");
            }
        } catch (StaleObjectStateException | ObjectOptimisticLockingFailureException e) {
            throw new ConcurrentModificationException(
                    "The confirmation token was modified or deleted by another transaction.");
        }
    }

    @Override
    public Long getUserIdByToken(String token) {
        Optional<ConfirmationToken> foundToken =
                confirmationTokenRepository.findConfirmationTokenByTokenValue(token);
        if (foundToken.isEmpty()) {
            throw new RuntimeException("Required confirmation token was not found");
        }
        return foundToken.get().getMember().getId();
    }

    @Override
    public void deleteConfirmationTokenByTokenValue(String token) {
        Optional<ConfirmationToken> foundToken =
                confirmationTokenRepository.findConfirmationTokenByTokenValue(token);
        if (foundToken.isEmpty()) {
            throw new RuntimeException("Required confirmation token was not found");
        }
        ConfirmationToken confirmationToken = foundToken.get();
        confirmationTokenRepository.delete(confirmationToken);
    }

    @Override
    public void findConfirmationTokenByTokenValue(String token) {
        Optional<ConfirmationToken> foundToken =
                confirmationTokenRepository.findConfirmationTokenByTokenValue(token);
        if (foundToken.isEmpty()) {
            throw new RuntimeException("Required confirmation token was not found");
        }
    }
}
