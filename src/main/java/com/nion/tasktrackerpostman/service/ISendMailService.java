package com.nion.tasktrackerpostman.service;

import jakarta.mail.MessagingException;
import lombok.NonNull;

public interface ISendMailService<T> {
    void sendMessage(@NonNull String to, @NonNull String from,
                     @NonNull String body, @NonNull String subject) throws MessagingException;
    void handleRegistration(T message) throws MessagingException;
    String returnHtmlContent(T message);
}
