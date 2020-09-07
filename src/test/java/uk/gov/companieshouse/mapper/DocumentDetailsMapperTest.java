package uk.gov.companieshouse.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.model.DocumentDetails;

import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.companyName;
import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.companyNumber;
import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.description;
import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class DocumentDetailsMapperTest {
    private final DocumentDetailsMapper mapper = new DocumentDetailsMapper();

    @Nested
    class ToEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((DocumentDetails) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            DocumentDetailsEntity mapped = mapper.map(new DocumentDetails(companyName, companyNumber, description, date));

            assertEquals(companyName, mapped.getCompanyName());
            assertEquals(companyNumber, mapped.getCompanyNumber());
            assertEquals(description, mapped.getDescription());
            assertEquals(date, mapped.getDate());
        }
    }

    @Nested
    class FromEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((DocumentDetailsEntity) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            DocumentDetails mapped = mapper.map(new DocumentDetailsEntity(companyName, companyNumber, description, date));

            assertEquals(companyName, mapped.getCompanyName());
            assertEquals(companyNumber, mapped.getCompanyNumber());
            assertEquals(description, mapped.getDescription());
            assertEquals(date, mapped.getDate());
        }
    }
}
