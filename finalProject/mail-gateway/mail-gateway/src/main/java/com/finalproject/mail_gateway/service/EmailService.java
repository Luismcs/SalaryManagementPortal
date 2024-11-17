package com.finalproject.mail_gateway.service;

import com.finalproject.mail_gateway.exception.EmailNotSentException;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmailWithAttachment(String to, String subject, String body, String fileName)  throws MessagingException, EmailNotSentException;

}
