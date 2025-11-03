package org.siri_hate.user_service.model.messages;

import org.siri_hate.user_service.model.enums.NotificationMessageType;

public record NotificationMessage(NotificationMessageType messageType, String userFullName, String userEmail) {
}