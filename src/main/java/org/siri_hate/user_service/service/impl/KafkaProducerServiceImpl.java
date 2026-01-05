package org.siri_hate.user_service.service.impl;

import org.siri_hate.user_service.model.messages.ConfirmationMessage;
import org.siri_hate.user_service.model.messages.NotificationMessage;
import org.siri_hate.user_service.model.messages.ProjectUpdateNotification;
import org.siri_hate.user_service.model.messages.UserDeletionMessage;
import org.siri_hate.user_service.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${notification.topic.name}")
    private String notificationTopicName;

    @Value("${confirmation.topic.name}")
    private String confirmationTopicName;

    @Value("${user.deletion.topic.s2m.name}")
    private String userDeletionTopic;

    @Value("${project.update.notification.topic.producer}")
    private String projectUpdateNotificationTopic;

    @Autowired
    public KafkaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendConfirmationToken(ConfirmationMessage confirmationMessage) {
        kafkaTemplate.send(confirmationTopicName, confirmationMessage);
    }

    @Override
    public void sendNotification(NotificationMessage notificationMessage) {
        kafkaTemplate.send(notificationTopicName, notificationMessage);
    }

    @Override
    public void sendUserDeletionMessage(String username) {
        UserDeletionMessage message = new UserDeletionMessage(username);
        kafkaTemplate.send(userDeletionTopic, message);
    }

    @Override
    public void sendProjectUpdateNotification(ProjectUpdateNotification notification) {
        kafkaTemplate.send(projectUpdateNotificationTopic, notification);
    }
}
