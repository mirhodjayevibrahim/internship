package com.internship.week5.task1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class NotificationFacadeTest {

    @Autowired
    private NotificationFacade notificationFacade;

    @Test
    void sendViaDefaultChannel() {
        assertDoesNotThrow(() ->
                notificationFacade.sendNotification("user@example.com", "Hello!"));
    }

    @Test
    void sendViaSpecificChannel() {
        assertDoesNotThrow(() ->
                notificationFacade.sendNotification("+1234567890", "Hello!", "sms"));
    }

    @Test
    void sendViaUnknownChannelThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                notificationFacade.sendNotification("user@example.com", "Hello!", "unknown"));
    }
}
