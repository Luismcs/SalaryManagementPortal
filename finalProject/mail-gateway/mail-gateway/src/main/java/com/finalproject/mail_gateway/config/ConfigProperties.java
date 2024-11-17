package com.finalproject.mail_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

    @Value("${sender-email}")
    private String senderEmail;

    @Value("${report-file-folder-path}")
    private String reportFileFolderPath;

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getReportFileFolderPath() {
        return reportFileFolderPath;
    }
}
