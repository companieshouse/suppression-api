package uk.gov.companieshouse.mapper;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.database.entity.PaymentDetailsEntity;
import uk.gov.companieshouse.mapper.base.Mapper;
import uk.gov.companieshouse.model.PaymentDetails;

@Component
public class PaymentDetailsMapper implements Mapper<PaymentDetailsEntity, PaymentDetails> {

    @Override
    public PaymentDetailsEntity map(PaymentDetails value) {
        if (value == null) {
            return null;
        }
        return new PaymentDetailsEntity(
            value.getReference(),
            value.getPaidAt(),
            value.getStatus()
        );
    }

    @Override
    public PaymentDetails map(PaymentDetailsEntity value) {
        if (value == null) {
            return null;
        }
        return new PaymentDetails(
            value.getReference(),
            value.getPaidAt(),
            value.getStatus()
        );
    }
}
