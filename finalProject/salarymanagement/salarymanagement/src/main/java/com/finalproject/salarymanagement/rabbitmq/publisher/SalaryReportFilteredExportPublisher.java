package com.finalproject.salarymanagement.rabbitmq.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalaryReportFilteredExportPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.export-filtered}")
    private String queueName;

    public SalaryReportFilteredExportPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishFilteredExportEvent(String fileName, String email) {
        rabbitTemplate.convertAndSend(queueName, eventInfoBuilder(fileName, email));
    }

    private String[] eventInfoBuilder(String fileName, String email) {
        String[] array = new String[2];

        array[0] = fileName;
        array[1] = email;

        return array;
    }

}
