package uk.gov.companieshouse;

import uk.gov.companieshouse.model.payment.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface TestData {
    interface Suppression {
        LocalDateTime createdAt = LocalDateTime.of(2010, 12, 31, 23, 59);
        String createdBy = "user@example.com";
        String applicationReference = "11111-11111";

        interface ApplicantDetails {
            String fullName = "USER#1";
            String previousName = "USER#2";
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

        interface PaymentDetails {
            String reference = "123";
            LocalDateTime paidAt = LocalDateTime.of(2010, 12, 31, 23, 59);
            PaymentStatus status = PaymentStatus.PAID;
        }
    }

    interface Payment {
        String etag = Suppression.etag;
        String kind = "suppression-request#payment";

        interface Links {
            String self = "/suppressions/" + Suppression.applicationReference;
            String payment = "/suppressions/" + Suppression.applicationReference + "/payment";
        }

        String companyNumber = Suppression.DocumentDetails.companyNumber;

        interface PaymentItem {
            String description = "Suppression application";
            String descriptionIdentifier = "Suppression application";
            Map<String, String> descriptionValues = Collections.emptyMap();
            String productType = "sr01";
            String amount = "32";
            List<String> availablePaymentMethods = Collections.singletonList("credit-card");
            List<String> classOfPayment = Collections.singletonList("data-maintenance");
            String kind = "suppression-request#payment-details";
            String resourceKind = "suppression-request#suppression-request";
        }
    }
}
