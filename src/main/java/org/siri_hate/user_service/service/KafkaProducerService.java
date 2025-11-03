package org.siri_hate.user_service.service;

import org.siri_hate.user_service.model.messages.ConfirmationMessage;
import org.siri_hate.user_service.model.messages.NotificationMessage;
import org.siri_hate.user_service.model.messages.ProjectUpdateNotification;

public interface KafkaProducerService {

    void sendConfirmationToken(ConfirmationMessage confirmationMessage);

    void sendNotification(NotificationMessage notificationMessage);

    void sendUserDeletionMessage(String username);

    void sendProjectUpdateNotification(ProjectUpdateNotification notification);
}
