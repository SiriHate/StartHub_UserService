package org.siri_hate.user_service.service;

public interface NotificationService {

    void sendSuccessfulRegistrationNotification(String name, String email);

    void sendDeletedAccountNotification(String name, String email);

    void sendChangedPasswordNotification(String name, String email);
}
