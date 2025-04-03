package com.notificationservice.web.dto;

import com.notificationservice.model.NotificationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationResponse {

    private UUID userId;

    private String subject;

    private String body;

    private NotificationStatus status;

    private LocalDateTime date;


}
