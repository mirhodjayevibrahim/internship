package com.internship.week5.task1;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {

    @Override
    public void send(String to, String message) {
        System.out.println("Sending EMAIL to " + to + ": " + message);
    }

    @Override
    public String getChannel() {
        return "email";
    }
}
