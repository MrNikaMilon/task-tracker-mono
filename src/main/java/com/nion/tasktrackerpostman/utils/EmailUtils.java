package com.nion.tasktrackerpostman.utils;

import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.regex.Pattern;

public class EmailUtils {
    public static boolean checkEmail(String email) {
        return !Pattern.compile("^\\S+@\\S+\\.\\S+$").matcher(email).matches();
    }

    public static void sendMail(String to, String from,
                                String body, String subject,
                                JavaMailSender mailSender) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message);

        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }
}
