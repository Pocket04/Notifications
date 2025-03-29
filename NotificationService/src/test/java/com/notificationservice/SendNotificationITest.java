package com.notificationservice;

import com.notificationservice.model.Notification;
import com.notificationservice.repository.NotificationRepository;
import com.notificationservice.service.NotificationService;
import com.notificationservice.web.dto.NotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class SendNotificationITest {
    @Autowired
    private NotificationService notificationService;

    private NotificationRequest notification;
    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setUp() {
        notification = NotificationRequest.builder()
                .subject("test")
                .body("test")
                .userId(UUID.randomUUID())
                .replyTo("test@test.com")
                .build();
    }

    @Test
    public void sendNotification_happyPath() {

        notificationService.sendNotification(notification);

        List<Notification> notifications = notificationRepository.findByUserId(notification.getUserId());
        assertThat(notifications).hasSize(1);
        Notification notification1 = notifications.getFirst();
        assertThat(notification1.getUserId()).isEqualTo(notification.getUserId());
    }
}
