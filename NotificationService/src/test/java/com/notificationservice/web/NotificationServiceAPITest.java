package com.notificationservice.web;

import com.notificationservice.model.NotificationStatus;
import com.notificationservice.service.NotificationService;
import com.notificationservice.web.dto.NotificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
public class NotificationServiceAPITest {
    @MockitoBean
    private NotificationService notificationService;
    @Autowired
    private MockMvc mockMvc;

    private NotificationResponse notificationResponse;

    @BeforeEach
    void setUp() {
        notificationResponse = NotificationResponse.builder()
                .subject("Test Subject")
                .body("Test Body")
                .status(NotificationStatus.SUCCEEDED)
                .date(LocalDateTime.now())
                .build();
    }

    @Test
    void getNotifications_returnsOKAndListOfNotificationResponses() throws Exception {

        when(notificationService.getAllNotifications(any())).thenReturn(List.of(notificationResponse));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/notifications")
                .param("userId", UUID.randomUUID().toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subject").isNotEmpty())
                .andExpect(jsonPath("$[0].body").isNotEmpty())
                .andExpect(jsonPath("$[0].status").isNotEmpty())
                .andExpect(jsonPath("$[0].date").isNotEmpty());
    }

}
