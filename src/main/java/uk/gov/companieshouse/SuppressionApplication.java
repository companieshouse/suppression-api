package uk.gov.companieshouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuppressionApplication {

    public static final String APPLICATION_NAMESPACE = "suppression-api";

    public static void main (String[] args) {
        SpringApplication.run(SuppressionApplication.class, args);
    }
}
