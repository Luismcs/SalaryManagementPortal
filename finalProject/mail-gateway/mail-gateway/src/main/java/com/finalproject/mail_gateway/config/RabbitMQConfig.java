package com.finalproject.mail_gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXPORT_QUEUE = "export-salaries-queue";
    public static final String EXPORT_FILTERED_QUEUE = "export-filtered-salaries-queue";

    @Bean
    public Queue exportQueue() {
        return new Queue(EXPORT_QUEUE, true);
    }

    @Bean
    public Queue exportFilteredQueue() {
        return new Queue(EXPORT_FILTERED_QUEUE, true);
    }

}
