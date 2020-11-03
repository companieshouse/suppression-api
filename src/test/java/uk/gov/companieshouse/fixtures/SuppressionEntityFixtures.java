package uk.gov.companieshouse.fixtures;

import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.database.entity.AddressEntity;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.database.entity.PaymentDetailsEntity;
import uk.gov.companieshouse.database.entity.SuppressionEntity;

public class SuppressionEntityFixtures {

    public static SuppressionEntity generateSuppressionEntity(String id) {
        return new SuppressionEntity(
            id,
            TestData.Suppression.createdAt,
            new ApplicantDetailsEntity(
                TestData.Suppression.ApplicantDetails.fullName,
                TestData.Suppression.ApplicantDetails.previousName,
                TestData.Suppression.ApplicantDetails.emailAddress,
                TestData.Suppression.ApplicantDetails.dateOfBirth
            ),
            new AddressEntity(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            new AddressEntity(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            new DocumentDetailsEntity[]{new DocumentDetailsEntity(
                TestData.Suppression.DocumentDetails.companyName,
                TestData.Suppression.DocumentDetails.companyNumber,
                TestData.Suppression.DocumentDetails.description,
                TestData.Suppression.DocumentDetails.date
            )},
            new AddressEntity(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            TestData.Suppression.etag,
            new PaymentDetailsEntity(
                TestData.Suppression.PaymentDetails.reference,
                TestData.Suppression.PaymentDetails.paidAt,
                TestData.Suppression.PaymentDetails.status
            )
        );
    }
}
