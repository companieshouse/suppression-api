package uk.gov.companieshouse.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.database.entity.AddressEntity;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.Address;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.DocumentDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.fullName;
import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.emailAddress;

import static uk.gov.companieshouse.TestData.Suppression.Address.line1;
import static uk.gov.companieshouse.TestData.Suppression.Address.line2;
import static uk.gov.companieshouse.TestData.Suppression.Address.town;
import static uk.gov.companieshouse.TestData.Suppression.Address.county;
import static uk.gov.companieshouse.TestData.Suppression.Address.postcode;
import static uk.gov.companieshouse.TestData.Suppression.Address.country;

import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.companyName;
import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.companyNumber;
import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.description;
import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.date;
import static uk.gov.companieshouse.TestData.Suppression.applicationReference;
import static uk.gov.companieshouse.TestData.Suppression.createdAt;
import static uk.gov.companieshouse.TestData.Suppression.etag;

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

            SuppressionEntity mapped = mapper.map(new Suppression(null, applicationReference,
                new ApplicantDetails(fullName, emailAddress),
                new Address(line1, line2, town, county, postcode, country),
                new Address(line1, line2, town, county, postcode, country),
                new DocumentDetails(companyName, companyNumber, description, date),
                etag
            ));

            assertNull(mapped.getCreatedAt());
            assertEquals(applicationReference, mapped.getId());

            assertEquals(fullName, mapped.getApplicantDetails().getFullName());
            assertEquals(emailAddress, mapped.getApplicantDetails().getEmailAddress());

            assertEquals(line1, mapped.getAddressToRemove().getLine1());
            assertEquals(line2, mapped.getAddressToRemove().getLine2());
            assertEquals(town, mapped.getAddressToRemove().getTown());
            assertEquals(county, mapped.getAddressToRemove().getCounty());
            assertEquals(postcode, mapped.getAddressToRemove().getPostcode());
            assertEquals(country, mapped.getAddressToRemove().getCountry());

            assertEquals(line1, mapped.getServiceAddress().getLine1());
            assertEquals(line2, mapped.getServiceAddress().getLine2());
            assertEquals(town, mapped.getServiceAddress().getTown());
            assertEquals(county, mapped.getServiceAddress().getCounty());
            assertEquals(postcode, mapped.getServiceAddress().getPostcode());

            assertEquals(companyName, mapped.getDocumentDetails().getCompanyName());
            assertEquals(companyNumber, mapped.getDocumentDetails().getCompanyNumber());
            assertEquals(description, mapped.getDocumentDetails().getDescription());
            assertEquals(date, mapped.getDocumentDetails().getDate());

            assertEquals(etag, mapped.getEtag());
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
            Suppression mapped = mapper.map(new SuppressionEntity(applicationReference, createdAt,
                new ApplicantDetailsEntity(fullName, emailAddress),
                new AddressEntity(line1, line2, town, county, postcode, country),
                new AddressEntity(line1, line2, town, county, postcode, country),
                new DocumentDetailsEntity(companyName, companyNumber, description, date),
                etag
            ));
            assertEquals(applicationReference, mapped.getApplicationReference());
            assertEquals(createdAt, mapped.getCreatedAt());
            assertEquals(fullName, mapped.getApplicantDetails().getFullName());
            assertEquals(emailAddress, mapped.getApplicantDetails().getEmailAddress());

            assertEquals(line1, mapped.getAddressToRemove().getLine1());
            assertEquals(line2, mapped.getAddressToRemove().getLine2());
            assertEquals(town, mapped.getAddressToRemove().getTown());
            assertEquals(county, mapped.getAddressToRemove().getCounty());
            assertEquals(postcode, mapped.getAddressToRemove().getPostcode());
            assertEquals(country, mapped.getAddressToRemove().getCountry());

            assertEquals(line1, mapped.getServiceAddress().getLine1());
            assertEquals(line2, mapped.getServiceAddress().getLine2());
            assertEquals(town, mapped.getServiceAddress().getTown());
            assertEquals(county, mapped.getServiceAddress().getCounty());
            assertEquals(postcode, mapped.getServiceAddress().getPostcode());

            assertEquals(companyName, mapped.getDocumentDetails().getCompanyName());
            assertEquals(companyNumber, mapped.getDocumentDetails().getCompanyNumber());
            assertEquals(description, mapped.getDocumentDetails().getDescription());
            assertEquals(date, mapped.getDocumentDetails().getDate());

            assertEquals(etag, mapped.getEtag());
        }
    }
}
