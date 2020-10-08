package uk.gov.companieshouse.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.SuppressionRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.dateOfBirth;
import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.emailAddress;
import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.fullName;
import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.previousName;
import static uk.gov.companieshouse.TestData.Suppression.applicationReference;
import static uk.gov.companieshouse.TestData.Suppression.createdAt;
import static uk.gov.companieshouse.TestData.Suppression.etag;

@ExtendWith(SpringExtension.class)
public class SuppressionRequestMapperTest {

    private final SuppressionRequestMapper mapper = new SuppressionRequestMapper(
        new ApplicantDetailsMapper()
    );

    @Nested
    class ToEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((SuppressionRequest) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {

            SuppressionEntity mapped = mapper.map(new SuppressionRequest(null, applicationReference,
                new ApplicantDetails(fullName, previousName, emailAddress, dateOfBirth),
                etag
            ));

            assertNull(mapped.getCreatedAt());
            assertEquals(applicationReference, mapped.getId());

            assertEquals(fullName, mapped.getApplicantDetails().getFullName());
            assertEquals(previousName, mapped.getApplicantDetails().getPreviousName());
            assertEquals(emailAddress, mapped.getApplicantDetails().getEmailAddress());
            assertEquals(dateOfBirth, mapped.getApplicantDetails().getDateOfBirth());

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
            SuppressionRequest mapped = mapper.map(new SuppressionEntity(applicationReference, createdAt,
                new ApplicantDetailsEntity(fullName, previousName, emailAddress, dateOfBirth),
                null,
                null,
                null,
                null,
                etag
            ));
            assertEquals(applicationReference, mapped.getApplicationReference());
            assertEquals(createdAt, mapped.getCreatedAt());

            assertEquals(fullName, mapped.getApplicantDetails().getFullName());
            assertEquals(previousName, mapped.getApplicantDetails().getPreviousName());
            assertEquals(emailAddress, mapped.getApplicantDetails().getEmailAddress());
            assertEquals(dateOfBirth, mapped.getApplicantDetails().getDateOfBirth());

            assertEquals(etag, mapped.getEtag());
        }
    }
}
