package com.nion.tasktrackerscheduler.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nion.tasktrackerscheduler.dto.response.StatisticResponse;
import com.nion.tasktrackerscheduler.mapper.IStatisticMapper;
import com.nion.tasktrackerscheduler.repository.IStatisticRepository;
import com.nion.tasktrackerscheduler.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/scheduler/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final IStatisticRepository repository;
    private final ScheduleService scheduleService;
    private final IStatisticMapper mapper;

    @GetMapping(path = "/get-statistic")
    public ResponseEntity<List<StatisticResponse>> getStatisticByUsers()
    {
        var userStatistic = repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        log.info("returned user statistic in db");
        return new ResponseEntity<>(userStatistic, HttpStatus.OK);
    }

    @PostMapping(path = "/send-statistic")
    public ResponseEntity<String> sendStatisticRequest() throws JsonProcessingException
    {
        scheduleService.sendStatisticByHttpClient();
        log.info("sending user statistic event to core service");
        return ResponseEntity.ok("Triggered successfully");
    }
}
