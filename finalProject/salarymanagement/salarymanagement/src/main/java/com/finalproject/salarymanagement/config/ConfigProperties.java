package com.finalproject.salarymanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

    @Value("${verify-salary-implementation-hour}")
    public int verifySalaryImplementationHour;

    @Value("${verify-salary-implementation-minute}")
    public int verifySalaryImplementationMinute;

    public int getVerifySalaryImplementationHour() {
        return verifySalaryImplementationHour;
    }

    public int getVerifySalaryImplementationMinute() {
        return verifySalaryImplementationMinute;
    }
}
