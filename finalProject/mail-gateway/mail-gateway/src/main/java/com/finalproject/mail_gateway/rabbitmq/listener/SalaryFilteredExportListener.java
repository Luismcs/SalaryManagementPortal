package com.finalproject.mail_gateway.rabbitmq.listener;

import com.finalproject.mail_gateway.exception.EmailNotSentException;
import com.finalproject.mail_gateway.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalaryFilteredExportListener {

    private final EmailServiceImpl emailService;

    public SalaryFilteredExportListener(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${queue.export-filtered}")
    public void listenExportEvent(String[] array) throws EmailNotSentException, MessagingException {
        emailService.sendEmailWithAttachment(array[1], "Salary Report", "Este é um teste", array[0]);
    }


}
