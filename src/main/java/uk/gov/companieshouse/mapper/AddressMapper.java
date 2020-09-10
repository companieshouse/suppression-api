package uk.gov.companieshouse.mapper;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.database.entity.AddressEntity;
import uk.gov.companieshouse.mapper.base.Mapper;
import uk.gov.companieshouse.model.Address;

@Component
public class AddressMapper implements Mapper<AddressEntity, Address> {

    @Override
    public AddressEntity map(Address value) {
        if (value == null) {
            return null;
        }
        return new AddressEntity(
            value.getLine1(),
            value.getLine2(),
            value.getTown(),
            value.getCounty(),
            value.getPostcode()
        );
    }

    @Override
    public Address map(AddressEntity value) {
        if (value == null) {
            return null;
        }
        return new Address(
            value.getLine1(),
            value.getLine2(),
            value.getTown(),
            value.getCounty(),
            value.getPostcode()
        );
    }
}
