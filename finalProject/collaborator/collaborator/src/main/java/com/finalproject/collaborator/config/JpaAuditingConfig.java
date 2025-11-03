package com.finalproject.collaborator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {

    public final UserContext userContext;

    public JpaAuditingConfig(UserContext userContext) {
        this.userContext = userContext;
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(userContext.getCurrentUser());
    }

}
