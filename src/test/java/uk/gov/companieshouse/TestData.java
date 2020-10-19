package uk.gov.companieshouse;

import java.time.LocalDateTime;

public interface TestData {
    interface Suppression {
        LocalDateTime createdAt = LocalDateTime.of(2010, 12, 31, 23, 59);
        String applicationReference = "11111-11111";

        interface ApplicantDetails {
            String fullName = "USER#1";
            String previousName = "USER#2";
            String emailAddress = "user@example.com";
            String dateOfBirth = "1980-05-01";
        }

        interface Address {
            String line1 = "HOUSE#1";
            String line2 = "STREET#1";
            String town = "TOWN#1";
            String county = "COUNTY#1";
            String postcode = "POSTCODE#1";
            String country = "COUNTRY#1";
        }

        interface DocumentDetails {
            String companyName = "COMPANYNAME#1";
            String companyNumber = "COMPANYNUMBER#1";
            String description = "DESCRIPTION";
            String date = "2000-01-01";
        }

        String etag = "123";
    }
}
