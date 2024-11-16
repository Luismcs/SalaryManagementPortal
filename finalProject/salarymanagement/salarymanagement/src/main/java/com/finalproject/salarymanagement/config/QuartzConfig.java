package com.finalproject.salarymanagement.config;

import com.finalproject.salarymanagement.scheduler.VerifySalaryActivationJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QuartzConfig {

    private final ConfigProperties configProperties;

    public QuartzConfig(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Bean
    public JobDetail salaryImplementationJobDetail() {
        return JobBuilder.newJob(VerifySalaryActivationJob.class)
                .withIdentity("salaryImplementationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger salaryImplementationJobTrigger() {
        CronScheduleBuilder scheduleBuilder =
                CronScheduleBuilder.dailyAtHourAndMinute(configProperties.verifySalaryImplementationHour,
                        configProperties.verifySalaryImplementationMinute);

        return TriggerBuilder.newTrigger()
                .forJob(salaryImplementationJobDetail())
                .withIdentity("salaryImplementationJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
