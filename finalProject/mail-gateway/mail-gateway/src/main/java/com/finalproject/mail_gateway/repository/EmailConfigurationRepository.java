package com.finalproject.mail_gateway.repository;

import com.finalproject.mail_gateway.model.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, Long> {
}
