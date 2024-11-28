package com.api.library.infra;

import com.api.library.config.ApplicationProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class BeanConfiguration {
}
