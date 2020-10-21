package uk.gov.companieshouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import static uk.gov.companieshouse.SuppressionApplication.APPLICATION_NAMESPACE;

@Configuration
public class LoggerConfig {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(APPLICATION_NAMESPACE);
    }
}
