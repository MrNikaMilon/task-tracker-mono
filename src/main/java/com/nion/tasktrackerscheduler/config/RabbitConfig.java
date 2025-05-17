package com.nion.tasktrackerscheduler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class RabbitConfig {
    public static final String STATISTIC_QUEUE = "statistic.user.queue";
    public static final String STATISTIC_EXCHANGE = "statistic.exchange";
    public static final String STATISTIC_ROUTING_KEY = "statistic.email";

    public static final String STATISTIC_RESPONSE_QUEUE = "statistic.response.queue";
    public static final String STATISTIC_RESPONSE_EXCHANGE = "statistic.response.exchange";
    public static final String STATISTIC_RESPONSE_ROUTING_KEY = "statistic.response";



    private final RabbitProperties rabbitProperties;

    public RabbitConfig(RabbitProperties rabbitProperties)
    {
            this.rabbitProperties = rabbitProperties;
    }

    @Bean
    public TopicExchange registrationExchange() {
            return new TopicExchange("registration.exchange", true, false);
        }

    @Bean
    public TopicExchange statisticExchange() {
            return new TopicExchange("statistic.exchange", true, false);
        }

    @Bean
    public TopicExchange statisticResponseExchange()
    {
            return new TopicExchange("statistic.response.exchange", true, false);
        }

    @Bean
    public Queue statisticQueue() {
            return new Queue(STATISTIC_QUEUE, true);
        }

    @Bean
    public Queue statisticResponseQueue() {
            return new Queue(STATISTIC_RESPONSE_QUEUE, true);
        }

    @Bean
    public Binding bindingStatistic()
    {
            return BindingBuilder
                    .bind(statisticQueue())
                    .to(statisticExchange())
                    .with(STATISTIC_ROUTING_KEY);
        }

    @Bean
    public Binding bindingStatisticResponse()
    {
            return BindingBuilder
                    .bind(statisticResponseQueue())
                    .to(statisticResponseExchange())
                    .with(STATISTIC_RESPONSE_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                             Jackson2JsonMessageConverter messageConverter)
    {
            var template = new RabbitTemplate(connectionFactory);
            template.setMessageConverter(messageConverter);
            return template;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter()
    {
            var objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return new Jackson2JsonMessageConverter(objectMapper);
        }

    @Bean
    public ConnectionFactory connectionFactory()
    {
            var factory = new CachingConnectionFactory(
                    rabbitProperties.getHost(),
                    rabbitProperties.getPort()
            );
            factory.setUsername(rabbitProperties.getUsername());
            factory.setPassword(rabbitProperties.getPassword());
            return factory;
        }
}

