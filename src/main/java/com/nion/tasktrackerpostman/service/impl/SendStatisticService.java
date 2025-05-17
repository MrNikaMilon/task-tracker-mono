package com.nion.tasktrackerpostman.service.impl;

import com.nion.tasktrackerpostman.dto.RabbitStatisticMessage;
import com.nion.tasktrackerpostman.service.ISendMailService;
import com.nion.tasktrackerpostman.utils.EmailUtils;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

import static com.nion.tasktrackerpostman.config.RabbitConfig.STATISTIC_RESPONSE_QUEUE;
import static com.nion.tasktrackerpostman.utils.EmailUtils.sendMail;

@Slf4j
@Service
public class SendStatisticService implements ISendMailService<RabbitStatisticMessage> {

    private final String from;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public SendStatisticService(@Value("${spring.mail.from}") String from,
                                JavaMailSender mailSender, TemplateEngine templateEngine)
    {
        this.from = from;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @RabbitListener(queues = STATISTIC_RESPONSE_QUEUE)
    @Transactional
    public void handleRegistration(RabbitStatisticMessage message) throws MessagingException
    {
        log.debug("received RabbitRegistrationMessage: {}", message);
        if(EmailUtils.checkEmail(message.email())){
            log.error("email not valid");
        }
        var subject = "Task Tracker";

        sendMessage(message.email(), from, returnHtmlContent(message), subject);
    }

    @Override
    public void sendMessage(@NonNull String to, @NonNull String from,
                            @NonNull String body, @NonNull String subject) throws MessagingException
    {
        sendMail(to, from, body, subject, mailSender);
        log.info("successfully send statistic email to user: {}", to);
    }

    @Override
    public String returnHtmlContent(RabbitStatisticMessage message)
    {
        var context = new Context();
        context.setVariable("username", message.email());
        context.setVariable("date", LocalDateTime.now().getDayOfWeek());
        context.setVariable("countTask", message.completedTask());
        context.setVariable("status", "COMPLETED");

        return templateEngine.process("email-form.html", context);
    }
}
