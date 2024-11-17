package com.finalproject.salarymanagement.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalaryReportExportPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.export}")
    private String queueName;

    public SalaryReportExportPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishExportEvent(String fileName) {
        rabbitTemplate.convertAndSend(queueName, fileName);
        System.out.println("Enviado");
    }

}
