package uk.gov.companieshouse.mapper;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.companieshouse.database.entity.AddressEntity;
import uk.gov.companieshouse.model.Address;

import static uk.gov.companieshouse.TestData.Suppression.Address.line1;
import static uk.gov.companieshouse.TestData.Suppression.Address.line2;
import static uk.gov.companieshouse.TestData.Suppression.Address.town;
import static uk.gov.companieshouse.TestData.Suppression.Address.county;
import static uk.gov.companieshouse.TestData.Suppression.Address.postcode;
import static uk.gov.companieshouse.TestData.Suppression.Address.country;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class AddressMapperTest {
    private final AddressMapper mapper = new AddressMapper();

    @Nested
    class ToEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((Address) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            Address address = new Address();
            address.setLine1(line1);
            address.setLine2(line2);
            address.setTown(town);
            address.setCounty(county);
            address.setPostcode(postcode);
            address.setCountry(country);

            AddressEntity mapped = mapper.map(address);
            assertEquals(line1, mapped.getLine1());
            assertEquals(line2, mapped.getLine2());
            assertEquals(town, mapped.getTown());
            assertEquals(county, mapped.getCounty());
            assertEquals(postcode, mapped.getPostcode());
            assertEquals(country, mapped.getCountry());
        }
    }

    @Nested
    class FromEntityMappingTest {
        @Test
        void shouldReturnNullWhenValueIsNull() {
            assertNull(mapper.map((AddressEntity) null));
        }

        @Test
        void shouldMapValueWhenValueIsNotNull() {
            Address mapped = mapper.map(new AddressEntity(line1, line2, town, county, postcode, country));

            assertEquals(line1, mapped.getLine1());
            assertEquals(line2, mapped.getLine2());
            assertEquals(town, mapped.getTown());
            assertEquals(county, mapped.getCounty());
            assertEquals(postcode, mapped.getPostcode());
            assertEquals(country, mapped.getCountry());
        }
    }
}
