package com.finalproject.mail_gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXPORT_QUEUE = "export-salaries-queue";

    @Bean
    public Queue exportQueue() {
        return new Queue(EXPORT_QUEUE, true);
    }

}
