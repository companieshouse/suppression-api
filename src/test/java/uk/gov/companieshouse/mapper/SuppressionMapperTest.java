package uk.gov.companieshouse.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.database.entity.AddressEntity;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.model.*;
import uk.gov.companieshouse.model.ApplicantDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class SuppressionMapperTest {
    private final SuppressionMapper mapper = new SuppressionMapper(
        new ApplicantDetailsMapper(),
        new AddressMapper(),
        new DocumentDetailsMapper()
    );

    @Nested
    class ToEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((Suppression) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            SuppressionEntity mapped = mapper.map(new Suppression(
                null,
                null,
                new ApplicantDetails(
                    TestData.Suppression.ApplicantDetails.fullName,
                    TestData.Suppression.ApplicantDetails.emailAddress
                ),
                new Address(
                    TestData.Suppression.Address.line1,
                    TestData.Suppression.Address.line2,
                    TestData.Suppression.Address.town,
                    TestData.Suppression.Address.county,
                    TestData.Suppression.Address.postcode
                ),
                new DocumentDetails(
                    TestData.Suppression.DocumentDetails.companyName,
                    TestData.Suppression.DocumentDetails.companyNumber,
                    TestData.Suppression.DocumentDetails.description,
                    TestData.Suppression.DocumentDetails.date
                )
            ));
            assertNull(mapped.getId());
            assertNull(mapped.getCreatedAt());
            assertEquals(TestData.Suppression.ApplicantDetails.fullName, mapped.getApplicantDetails().getFullName());
            assertEquals(TestData.Suppression.ApplicantDetails.emailAddress, mapped.getApplicantDetails().getEmailAddress());

            assertEquals(TestData.Suppression.Address.line1, mapped.getAddressToRemove().getLine1());
            assertEquals(TestData.Suppression.Address.line2, mapped.getAddressToRemove().getLine2());
            assertEquals(TestData.Suppression.Address.town, mapped.getAddressToRemove().getTown());
            assertEquals(TestData.Suppression.Address.county, mapped.getAddressToRemove().getCounty());
            assertEquals(TestData.Suppression.Address.postcode, mapped.getAddressToRemove().getPostcode());

            assertEquals(TestData.Suppression.DocumentDetails.companyName, mapped.getDocumentDetails().getCompanyName());
            assertEquals(TestData.Suppression.DocumentDetails.companyNumber, mapped.getDocumentDetails().getCompanyNumber());
            assertEquals(TestData.Suppression.DocumentDetails.description, mapped.getDocumentDetails().getDescription());
            assertEquals(TestData.Suppression.DocumentDetails.date, mapped.getDocumentDetails().getDate());
        }
    }

    @Nested
    class FromEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((SuppressionEntity) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            Suppression mapped = mapper.map(new SuppressionEntity(
                TestData.Suppression.id,
                TestData.Suppression.createdAt,
                new ApplicantDetailsEntity(
                    TestData.Suppression.ApplicantDetails.fullName,
                    TestData.Suppression.ApplicantDetails.emailAddress
                ),
                new AddressEntity(
                    TestData.Suppression.Address.line1,
                    TestData.Suppression.Address.line2,
                    TestData.Suppression.Address.town,
                    TestData.Suppression.Address.county,
                    TestData.Suppression.Address.postcode
                ),
                new DocumentDetailsEntity(
                    TestData.Suppression.DocumentDetails.companyName,
                    TestData.Suppression.DocumentDetails.companyNumber,
                    TestData.Suppression.DocumentDetails.description,
                    TestData.Suppression.DocumentDetails.date
                )
            ));
            assertEquals(TestData.Suppression.id, mapped.getId());
            assertEquals(TestData.Suppression.createdAt, mapped.getCreatedAt());
            assertEquals(TestData.Suppression.ApplicantDetails.fullName, mapped.getApplicantDetails().getFullName());
            assertEquals(TestData.Suppression.ApplicantDetails.emailAddress, mapped.getApplicantDetails().getEmailAddress());

            assertEquals(TestData.Suppression.Address.line1, mapped.getAddressToRemove().getLine1());
            assertEquals(TestData.Suppression.Address.line2, mapped.getAddressToRemove().getLine2());
            assertEquals(TestData.Suppression.Address.town, mapped.getAddressToRemove().getTown());
            assertEquals(TestData.Suppression.Address.county, mapped.getAddressToRemove().getCounty());
            assertEquals(TestData.Suppression.Address.postcode, mapped.getAddressToRemove().getPostcode());

            assertEquals(TestData.Suppression.DocumentDetails.companyName, mapped.getDocumentDetails().getCompanyName());
            assertEquals(TestData.Suppression.DocumentDetails.companyNumber, mapped.getDocumentDetails().getCompanyNumber());
            assertEquals(TestData.Suppression.DocumentDetails.description, mapped.getDocumentDetails().getDescription());
            assertEquals(TestData.Suppression.DocumentDetails.date, mapped.getDocumentDetails().getDate());
        }
    }
}
