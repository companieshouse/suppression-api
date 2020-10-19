package uk.gov.companieshouse.fixtures;

import java.sql.Timestamp;

import uk.gov.companieshouse.model.payment.PaymentPatchRequest;
import uk.gov.companieshouse.model.payment.PaymentStatus;

public class PaymentFixtures {
    
    public static PaymentPatchRequest generatePaymentPatchRequest(PaymentStatus status) {
        return new PaymentPatchRequest(
            status,
            "TESTREF123",
            new Timestamp(1)
        );
    }
}
