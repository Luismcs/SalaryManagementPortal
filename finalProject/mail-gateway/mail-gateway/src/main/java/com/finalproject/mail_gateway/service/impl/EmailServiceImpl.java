package com.finalproject.mail_gateway.service.impl;

import com.finalproject.mail_gateway.config.ConfigProperties;
import com.finalproject.mail_gateway.enums.ErrorResponseCode;
import com.finalproject.mail_gateway.exception.EmailNotSentException;
import com.finalproject.mail_gateway.exception.ErrorMessage;
import com.finalproject.mail_gateway.model.EmailConfiguration;
import com.finalproject.mail_gateway.repository.EmailConfigurationRepository;
import com.finalproject.mail_gateway.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailConfigurationRepository emailConfigurationRepository;
    private final ConfigProperties configProperties;

    public EmailServiceImpl(JavaMailSender mailSender, EmailConfigurationRepository emailConfigurationRepository,
                            ConfigProperties configProperties) {
        this.mailSender = mailSender;
        this.emailConfigurationRepository = emailConfigurationRepository;
        this.configProperties = configProperties;
    }

    public void sendEmailWithAttachment(String to, String subject, String body, String fileName) throws
            MessagingException,
            EmailNotSentException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = mimeMessageHelperBuilder(mimeMessage, to, subject, body);
        String filePath = configProperties.getReportFileFolderPath() + fileName;
        FileSystemResource file = new FileSystemResource(new File(filePath));

        if (file.exists()) {
            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
        } else {
            throw new EmailNotSentException(ErrorResponseCode.EMAIL_SENDER_ERROR, 500,
                    ErrorMessage.EXCEL_FILE_NOT_FOUND, fileName);
        }

        mailSender.send(mimeMessage);
        saveEmailInfo(to, subject, body);
    }

    private MimeMessageHelper mimeMessageHelperBuilder(MimeMessage mimeMessage, String to, String subject, String body)
            throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        helper.setFrom(configProperties.getSenderEmail());

        return helper;
    }

    private void saveEmailInfo(String to, String subject, String body) {
        emailConfigurationRepository.save(new EmailConfiguration(
                "GeneralSalaryReportEmail",
                "Salary report for all collaborators",
                subject,
                to,
                body));
    }

}
