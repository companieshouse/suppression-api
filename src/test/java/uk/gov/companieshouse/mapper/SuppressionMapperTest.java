package uk.gov.companieshouse.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.database.entity.AddressEntity;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.database.entity.PaymentDetailsEntity;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.model.Address;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.DocumentDetails;
import uk.gov.companieshouse.model.PaymentDetails;
import uk.gov.companieshouse.model.Suppression;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.fullName;
import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.previousName;
import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.dateOfBirth;

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
import static uk.gov.companieshouse.TestData.Suppression.createdBy;
import static uk.gov.companieshouse.TestData.Suppression.etag;

import static uk.gov.companieshouse.TestData.Suppression.PaymentDetails.reference;
import static uk.gov.companieshouse.TestData.Suppression.PaymentDetails.paidAt;
import static uk.gov.companieshouse.TestData.Suppression.PaymentDetails.status;

@ExtendWith(SpringExtension.class)
public class SuppressionMapperTest {
    private final SuppressionMapper mapper = new SuppressionMapper(
        new ApplicantDetailsMapper(),
        new AddressMapper(),
        new DocumentDetailsMapper(),
        new PaymentDetailsMapper()
    );

    @Nested
    class ToEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((Suppression) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {

            SuppressionEntity mapped = mapper.map(new Suppression(null, createdBy, applicationReference,
                new ApplicantDetails(fullName, previousName, dateOfBirth),
                new Address(line1, line2, town, county, postcode, country),
                new Address(line1, line2, town, county, postcode, country),
                new DocumentDetails(companyName, companyNumber, description, date),
                new Address(line1, line2, town, county, postcode, country),
                etag,
                new PaymentDetails(reference, paidAt, status)
            ));

            assertNull(mapped.getCreatedAt());
            assertEquals(createdBy, mapped.getCreatedBy());
            assertEquals(applicationReference, mapped.getId());

            assertEquals(fullName, mapped.getApplicantDetails().getFullName());
            assertEquals(previousName, mapped.getApplicantDetails().getPreviousName());
            assertEquals(dateOfBirth, mapped.getApplicantDetails().getDateOfBirth());

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
            assertEquals(country, mapped.getServiceAddress().getCountry());

            assertEquals(1, mapped.getDocumentDetails().size());
            assertEquals(companyName, mapped.getDocumentDetails().get(0).getCompanyName());
            assertEquals(companyNumber, mapped.getDocumentDetails().get(0).getCompanyNumber());
            assertEquals(description, mapped.getDocumentDetails().get(0).getDescription());
            assertEquals(date, mapped.getDocumentDetails().get(0).getDate());

            assertEquals(etag, mapped.getEtag());

            assertEquals(reference, mapped.getPaymentDetails().getReference());
            assertEquals(paidAt, mapped.getPaymentDetails().getPaidAt());
            assertEquals(status, mapped.getPaymentDetails().getStatus());
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
            Suppression mapped = mapper.map(new SuppressionEntity(applicationReference, createdAt, createdBy,
                new ApplicantDetailsEntity(fullName, previousName, dateOfBirth),
                new AddressEntity(line1, line2, town, county, postcode, country),
                new AddressEntity(line1, line2, town, county, postcode, country),
                new ArrayList<>(Collections.singletonList(
                    new DocumentDetailsEntity(companyName, companyNumber, description, date)
                )),
                new AddressEntity(line1, line2, town, county, postcode, country),
                etag,
                new PaymentDetailsEntity(reference, paidAt, status)
            ));
            assertEquals(applicationReference, mapped.getApplicationReference());
            assertEquals(createdAt, mapped.getCreatedAt());
            assertEquals(createdBy, mapped.getCreatedBy());

            assertEquals(fullName, mapped.getApplicantDetails().getFullName());
            assertEquals(previousName, mapped.getApplicantDetails().getPreviousName());
            assertEquals(dateOfBirth, mapped.getApplicantDetails().getDateOfBirth());

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
            assertEquals(country, mapped.getServiceAddress().getCountry());

            assertEquals(companyName, mapped.getDocumentDetails().getCompanyName());
            assertEquals(companyNumber, mapped.getDocumentDetails().getCompanyNumber());
            assertEquals(description, mapped.getDocumentDetails().getDescription());
            assertEquals(date, mapped.getDocumentDetails().getDate());

            assertEquals(etag, mapped.getEtag());

            assertEquals(reference, mapped.getPaymentDetails().getReference());
            assertEquals(paidAt, mapped.getPaymentDetails().getPaidAt());
            assertEquals(status, mapped.getPaymentDetails().getStatus());
        }
    }
}
