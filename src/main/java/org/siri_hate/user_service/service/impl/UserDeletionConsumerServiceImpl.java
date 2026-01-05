package org.siri_hate.user_service.service.impl;

import com.google.gson.Gson;
import org.siri_hate.user_service.model.messages.UserDeletionMessage;
import org.siri_hate.user_service.service.UserDeletionConsumerService;
import org.siri_hate.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDeletionConsumerServiceImpl implements UserDeletionConsumerService {

    private final UserService userService;
    private final Gson gson;

    @Autowired
    public UserDeletionConsumerServiceImpl(UserService userService, Gson gson) {
        this.userService = userService;
        this.gson = gson;
    }

    @Override
    @KafkaListener(topics = "${user.deletion.topic.m2s.name}", groupId = "${spring.application.name}")
    @Transactional
    public void consumeUserDeletionMessage(String message) {
        UserDeletionMessage userDeletionMessage = gson.fromJson(message, UserDeletionMessage.class);
        String username = userDeletionMessage.username();
        userService.deleteUserByUsername(username);
    }
}