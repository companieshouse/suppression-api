package uk.gov.companieshouse.fixtures;

import uk.gov.companieshouse.model.Address;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.DocumentDetails;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.TestData;

public class SuppressionFixtures {
    
    public static Suppression generateSuppression(String reference) {
        return new Suppression(
            TestData.Suppression.createdAt,
            reference,
            new ApplicantDetails(
                TestData.Suppression.ApplicantDetails.fullName,
                TestData.Suppression.ApplicantDetails.previousName,
                TestData.Suppression.ApplicantDetails.emailAddress,
                TestData.Suppression.ApplicantDetails.dateOfBirth
            ),
            new Address(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            new Address(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            new DocumentDetails(
                TestData.Suppression.DocumentDetails.companyName,
                TestData.Suppression.DocumentDetails.companyNumber,
                TestData.Suppression.DocumentDetails.description,
                TestData.Suppression.DocumentDetails.date
            ),
            new Address(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            TestData.Suppression.etag
        );
    }
}
