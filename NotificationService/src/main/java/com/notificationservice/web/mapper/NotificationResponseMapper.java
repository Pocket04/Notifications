package com.notificationservice.web.mapper;

import com.notificationservice.model.Notification;
import com.notificationservice.web.dto.NotificationResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NotificationResponseMapper {

    public static NotificationResponse mapNotificationResponse(Notification notification) {

        return NotificationResponse.builder()
                .subject(notification.getSubject())
                .body(notification.getBody())
                .status(notification.getStatus())
                .date(notification.getDate())
                .userId(notification.getUserId())
                .build();
    }

}
