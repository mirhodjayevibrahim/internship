package com.internship.week5.task1;

public interface NotificationService {

    void send(String to, String message);

    String getChannel();
}
