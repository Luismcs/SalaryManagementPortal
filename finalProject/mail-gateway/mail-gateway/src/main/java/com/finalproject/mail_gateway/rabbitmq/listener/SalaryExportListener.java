package com.finalproject.mail_gateway.rabbitmq.listener;

import com.finalproject.mail_gateway.exception.EmailNotSentException;
import com.finalproject.mail_gateway.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalaryExportListener {

    private final EmailServiceImpl emailService;

    public SalaryExportListener(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${queue.export}")
    public void listenExportEvent(String[] array) throws EmailNotSentException, MessagingException, InterruptedException {
        emailService.sendEmailWithAttachment(array[1], "Salary Report", "Este é um teste", array[0]);
        Thread.sleep(2000);
    }


}
