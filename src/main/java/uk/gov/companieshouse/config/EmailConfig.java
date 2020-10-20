package uk.gov.companieshouse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${email.chEmail}")
    private String chEmail;

    @Value("${email.processingDelayEvent:}")
    private String processingDelayEvent;

    public String getChEmail() { return chEmail; }

    public String getProcessingDelayEvent() { return processingDelayEvent; }

}
