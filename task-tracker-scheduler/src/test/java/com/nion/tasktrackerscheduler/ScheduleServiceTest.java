package com.nion.tasktrackerscheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nion.tasktrackerscheduler.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    ObjectMapper objectMapper;

    @Mock
    HttpClient httpClient;

    @Mock
    HttpResponse<String> response;

    @InjectMocks
    ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        scheduleService = new ScheduleService("http://localhost:8090/api/v1/statistic/get_statistic", "task-tracker-scheduler", objectMapper) {
            @Override
            protected HttpClient createHttpClient() {
                return httpClient;
            }
        };
    }

    @Test
    void test_send_midnight_request_successful() throws Exception {
        when(response.statusCode()).thenReturn(200);

        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class))
        ).thenReturn(response);

        scheduleService.sendStatisticByHttpClient();

        verify(httpClient, times(1)).send(any(), any());
    }

    @Test
    void test_send_midnight_request_failed() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("connection exception"));

        assertThrows(RuntimeException.class, () -> scheduleService.sendStatisticByHttpClient());
    }
}
