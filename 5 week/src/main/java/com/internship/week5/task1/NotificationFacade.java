package com.internship.week5.task1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationFacade {

    private final Map<String, NotificationService> serviceMap;
    private final String defaultChannel;

    public NotificationFacade(List<NotificationService> services,
                              @Value("${notification.default-channel}") String defaultChannel) {
        this.serviceMap = services.stream()
                .collect(Collectors.toMap(NotificationService::getChannel, Function.identity()));
        this.defaultChannel = defaultChannel;
    }

    public void sendNotification(String to, String message) {
        sendNotification(to, message, defaultChannel);
    }

    public void sendNotification(String to, String message, String channel) {
        NotificationService service = serviceMap.get(channel);
        if (service == null) {
            throw new IllegalArgumentException("Unknown notification channel: " + channel);
        }
        service.send(to, message);
    }
}
