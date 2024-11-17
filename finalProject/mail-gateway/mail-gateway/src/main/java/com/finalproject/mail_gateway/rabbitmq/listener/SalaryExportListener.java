package com.finalproject.mail_gateway.rabbitmq.listener;

import com.finalproject.mail_gateway.exception.EmailNotSentException;
import com.finalproject.mail_gateway.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalaryExportListener {

    @Value("${queue.export}")
    private String queueName;

    private final EmailServiceImpl emailService;

    public SalaryExportListener(EmailServiceImpl emailService){
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${queue.export}")
    public void listenExportEvent(String fileName) throws EmailNotSentException, MessagingException {
        emailService.sendEmailWithAttachment("luismcssmurf@gmail.com", "Assunto", "Este é um teste", fileName);
    }


}
