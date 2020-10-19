package uk.gov.companieshouse.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.database.entity.PaymentDetailsEntity;
import uk.gov.companieshouse.model.PaymentDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static uk.gov.companieshouse.TestData.Suppression.PaymentDetails.paidAt;
import static uk.gov.companieshouse.TestData.Suppression.PaymentDetails.reference;
import static uk.gov.companieshouse.TestData.Suppression.PaymentDetails.status;

@ExtendWith(SpringExtension.class)
public class PaymentDetailsMapperTest {
    private final PaymentDetailsMapper mapper = new PaymentDetailsMapper();

    @Nested
    class ToEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((PaymentDetails) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            PaymentDetailsEntity mapped = mapper.map(new PaymentDetails(reference, paidAt, status));

            assertEquals(reference, mapped.getReference());
            assertEquals(paidAt, mapped.getPaidAt());
            assertEquals(status, mapped.getStatus());
        }
    }

    @Nested
    class FromEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((PaymentDetailsEntity) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            PaymentDetails mapped = mapper.map(new PaymentDetailsEntity(reference, paidAt, status));

            assertEquals(reference, mapped.getReference());
            assertEquals(paidAt, mapped.getPaidAt());
            assertEquals(status, mapped.getStatus());
        }
    }
}
