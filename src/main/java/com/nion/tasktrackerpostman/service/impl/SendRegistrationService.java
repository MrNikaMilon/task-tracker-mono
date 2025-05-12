package com.nion.tasktrackerpostman.service.impl;

import com.nion.tasktrackerpostman.dto.RabbitRegistrationMessage;
import com.nion.tasktrackerpostman.service.ISendMailService;
import com.nion.tasktrackerpostman.utils.CheckDataUtils;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.nion.tasktrackerpostman.config.RabbitConfig.REG_QUEUE;

@Slf4j
@Service
public class SendRegistrationService implements ISendMailService<RabbitRegistrationMessage> {

    private final String from;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public SendRegistrationService(
            @Value("${spring.mail.from}") String from,
            JavaMailSender mailSender, TemplateEngine templateEngine)
    {
        this.from = from;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @RabbitListener(queues = REG_QUEUE)
    @Transactional
    public void handleRegistration(RabbitRegistrationMessage message) throws MessagingException
    {
        log.debug("received RabbitRegistrationMessage: {}", message);
        if(!CheckDataUtils.checkEmail(message.email())){
            log.error("email not valid");
        }
        var subject = "Task Tracker";

        sendMessage(message.email(), from, returnHtmlContent(message), subject);
    }

    @Override
    public void sendMessage(@NonNull String to, @NonNull String from,
                            @NonNull String body, @NonNull String subject) throws MessagingException
    {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message);

        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
        log.info("successfully send registration email to user: {}", to);
    }

    @Override
    public String returnHtmlContent(RabbitRegistrationMessage message)
    {
        var context = new Context();
        context.setVariable("username", message.email());
        return templateEngine.process("email-form.html", context);
    }
}
