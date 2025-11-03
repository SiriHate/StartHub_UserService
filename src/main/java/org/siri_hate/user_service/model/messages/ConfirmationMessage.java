package org.siri_hate.user_service.model.messages;

import org.siri_hate.user_service.model.enums.ConfirmationMessageType;

public record ConfirmationMessage(ConfirmationMessageType messageType, String userFullName, String userEmail,
                                  String userConfirmationToken) {
}
