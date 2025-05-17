package com.nion.tasktrackerscheduler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nion.tasktrackerscheduler.dto.request.RequestUserStatistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ScheduleService {

    private final String serviceName;
    private final HttpClient httpClient;
    private final String coreServiceAddress;
    private final ObjectMapper objectMapper;

    public ScheduleService(@Value("${external.service.url}")
                           String coreServiceAddress,
                           @Value("${spring.application.name}")
                           String serviceName,
                           ObjectMapper objectMapper)
    {
        this.httpClient = createHttpClient();
        this.coreServiceAddress = coreServiceAddress;
        this.serviceName = serviceName;
        this.objectMapper = objectMapper;
    }

    protected HttpClient createHttpClient()
    {
        return HttpClient.newHttpClient();
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Moscow")
    public void sendStatisticByHttpClient() throws JsonProcessingException
    {
        var body = RequestUserStatistic.builder()
                .serviceName(serviceName)
                .sendTime(LocalDateTime.now())
                .build();

        var requestBody = objectMapper.writeValueAsString(body);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(coreServiceAddress))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                log.error("failed to get successful response code from core service");
                throw new RuntimeException();
            }
            log.info("successful scheduled send request");

        } catch (IOException | InterruptedException e){
            throw new RuntimeException("http request to address %s, is fall".formatted(coreServiceAddress));
        }
    }
}
