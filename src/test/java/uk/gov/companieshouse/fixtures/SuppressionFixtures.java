package uk.gov.companieshouse.fixtures;

import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.model.Address;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.DocumentDetails;
import uk.gov.companieshouse.model.PaymentDetails;
import uk.gov.companieshouse.model.Suppression;

public class SuppressionFixtures {

    public static Suppression generateSuppression(String reference) {
        return new Suppression(
            TestData.Suppression.createdAt,
            TestData.Suppression.createdBy,
            reference,
            generateApplicantDetails(),
            generateAddress(),
            generateAddress(),
            generateDocumentDetails(),
            generateAddress(),
            TestData.Suppression.etag,
            generatePaymentDetails()
        );
    }

    public static ApplicantDetails generateApplicantDetails() {
        return new ApplicantDetails(
            TestData.Suppression.ApplicantDetails.fullName,
            TestData.Suppression.ApplicantDetails.previousName,
            TestData.Suppression.ApplicantDetails.dateOfBirth
        );
    }

    public static DocumentDetails generateDocumentDetails() {
        return new DocumentDetails(
            TestData.Suppression.DocumentDetails.companyName,
            TestData.Suppression.DocumentDetails.companyNumber,
            TestData.Suppression.DocumentDetails.description,
            TestData.Suppression.DocumentDetails.date
        );
    }

    public static PaymentDetails generatePaymentDetails() {
        return new PaymentDetails(
            TestData.Suppression.PaymentDetails.reference,
            TestData.Suppression.PaymentDetails.paidAt,
            TestData.Suppression.PaymentDetails.status
        );
    }

    public static Address generateAddress() {
        return new Address(
            TestData.Suppression.Address.line1,
            TestData.Suppression.Address.line2,
            TestData.Suppression.Address.town,
            TestData.Suppression.Address.county,
            TestData.Suppression.Address.postcode,
            TestData.Suppression.Address.country
        );
    }
}
