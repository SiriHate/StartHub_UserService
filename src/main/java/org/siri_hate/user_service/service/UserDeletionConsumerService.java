package org.siri_hate.user_service.service;

public interface UserDeletionConsumerService {

    void consumeUserDeletionMessage(String message);
} 