package com.notificationservice.web.dto;

import com.notificationservice.model.NotificationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationResponse {

    private UUID userId;

    private String subject;

    private String body;

    private NotificationStatus status;

    private LocalDateTime date;


}
