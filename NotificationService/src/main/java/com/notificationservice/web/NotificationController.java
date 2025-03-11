package com.notificationservice.web;

import com.notificationservice.model.Notification;
import com.notificationservice.service.NotificationService;
import com.notificationservice.web.dto.NotificationRequest;
import com.notificationservice.web.dto.NotificationResponse;
import com.notificationservice.web.mapper.NotificationResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody NotificationRequest notificationRequest) {

        Notification notification = notificationService.sendNotification(notificationRequest);

        NotificationResponse notificationResponse = NotificationResponseMapper.mapNotificationResponse(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationResponse);
    }
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(@RequestParam UUID userId) {

        return ResponseEntity.status(HttpStatus.OK)
                        .body(notificationService.getAllNotifications(userId));
    }

}
