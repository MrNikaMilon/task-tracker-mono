package com.nion.tasktrackerscheduler.service;

import com.nion.tasktrackerscheduler.dto.message.from.RabbitStatisticMessage;
import com.nion.tasktrackerscheduler.mapper.IStatisticMapper;
import com.nion.tasktrackerscheduler.repository.IStatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.nion.tasktrackerscheduler.config.RabbitConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {

    private final IStatisticMapper mapper;
    private final IStatisticRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    @RabbitListener(queues = STATISTIC_RESPONSE_QUEUE)
    void handleStatisticResponseMessage(RabbitStatisticMessage message) throws MessagingException
    {
        var statisticEntity = mapper.toEntity(message);
        repository.save(statisticEntity);
        log.info("saved statistic info");
    }

    @Transactional
    void sendStatisticInfo()
    {
        var statistic = repository.findAllByCreatedDate(LocalDate.now());
        statistic
                .stream()
                .map(mapper::toResponse)
                .forEach(message -> rabbitTemplate.convertAndSend(STATISTIC_EXCHANGE, STATISTIC_ROUTING_KEY, message));
    }
}
