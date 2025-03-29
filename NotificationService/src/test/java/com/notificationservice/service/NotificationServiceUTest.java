package com.notificationservice.service;

import com.notificationservice.model.Notification;
import com.notificationservice.model.NotificationStatus;
import com.notificationservice.repository.NotificationRepository;
import com.notificationservice.web.dto.NotificationResponse;
import com.notificationservice.web.mapper.NotificationResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceUTest {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private MailSender mailSender;
    @InjectMocks
    private NotificationService notificationService;

    private Notification notification;
    private Notification notification2;


    @BeforeEach
    void setUp() {
        notification = Notification.builder()
                .isDeleted(false)
                .subject("Test Subject")
                .body("Test Body")
                .status(NotificationStatus.SUCCEEDED)
                .date(LocalDateTime.now())
                .build();
        notification2 = Notification.builder()
                .isDeleted(false)
                .subject("Test Subject")
                .body("Test Body")
                .status(NotificationStatus.SUCCEEDED)
                .date(LocalDateTime.now())
                .build();
    }


    @Test
    void getAllNotifications_returnsListOfNotDeletedNotifications() {
        Notification notification3 = new Notification();
        notification3.setDeleted(true);
        NotificationResponse response1 = NotificationResponseMapper.mapNotificationResponse(notification);
        NotificationResponse response2 = NotificationResponseMapper.mapNotificationResponse(notification2);

        when(notificationRepository.findByUserId(any())).thenReturn(List.of(notification, notification2, notification3));

        assertEquals(List.of(response1, response2), notificationService.getAllNotifications(UUID.randomUUID()));
        verify(notificationRepository, times(1)).findByUserId(any());
    }

}
