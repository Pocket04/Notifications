package com.notificationservice.web.dto;

import com.notificationservice.model.NotificationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private String subject;

    private String body;

    private NotificationStatus status;

    private LocalDateTime date;


}
