package com.notificationservice.service;

import com.notificationservice.model.Notification;
import com.notificationservice.model.NotificationStatus;
import com.notificationservice.repository.NotificationRepository;
import com.notificationservice.web.dto.NotificationRequest;
import com.notificationservice.web.dto.NotificationResponse;
import com.notificationservice.web.mapper.NotificationResponseMapper;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MailSender mailSender;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, MailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    public Notification sendNotification(NotificationRequest notificationRequest) {

        SimpleMailMessage message = new SimpleMailMessage();

        Notification notification = Notification.builder()
                .subject(notificationRequest.getSubject())
                .body(notificationRequest.getBody())
                .userId(notificationRequest.getUserId())
                .replyTo(notificationRequest.getReplyTo())
                .isDeleted(false)
                .date(LocalDateTime.now())
                .build();


        message.setTo("pockito69@gmail.com");
        message.setSubject(notificationRequest.getSubject());
        message.setText(notificationRequest.getBody());
        message.setReplyTo(notificationRequest.getReplyTo());
        try {
            mailSender.send(message);
            notification.setStatus(NotificationStatus.SUCCEEDED);
        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            log.warn("There was an error sending the email from %s due to %s".formatted(notificationRequest.getReplyTo(), e.getMessage()));
        }
        notificationRepository.save(notification);
        return notification;
    }

    public List<NotificationResponse> getAllNotifications(UUID userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification : notifications) {
            if (!notification.isDeleted()) {
                notificationResponses.add(NotificationResponseMapper.mapNotificationResponse(notification));
            }
        }
        return notificationResponses;
    }

}
