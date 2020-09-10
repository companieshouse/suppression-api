package uk.gov.companieshouse.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.model.ApplicantDetails;

import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.fullName;
import static uk.gov.companieshouse.TestData.Suppression.ApplicantDetails.emailAddress;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class ApplicantDetailsMapperTest {
    private final ApplicantDetailsMapper mapper = new ApplicantDetailsMapper();

    @Nested
    class ToEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((ApplicantDetails) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            ApplicantDetailsEntity mapped = mapper.map(new ApplicantDetails(fullName, emailAddress));

            assertEquals(fullName, mapped.getFullName());
            assertEquals(emailAddress, mapped.getEmailAddress());
        }
    }

    @Nested
    class FromEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((ApplicantDetailsEntity) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            ApplicantDetails mapped = mapper.map(new ApplicantDetailsEntity(fullName, emailAddress));

            assertEquals(fullName, mapped.getFullName());
            assertEquals(emailAddress, mapped.getEmailAddress());
        }
    }
}
