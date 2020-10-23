package uk.gov.companieshouse.fixtures;

import java.sql.Timestamp;
import java.util.Collections;

import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.model.payment.Links;
import uk.gov.companieshouse.model.payment.Payment;
import uk.gov.companieshouse.model.payment.PaymentItem;
import uk.gov.companieshouse.model.payment.PaymentPatchRequest;
import uk.gov.companieshouse.model.payment.PaymentStatus;

public class PaymentFixtures {

    public static PaymentPatchRequest generatePaymentPatchRequest(PaymentStatus status) {
        return new PaymentPatchRequest(
            status,
            TestData.Suppression.PaymentDetails.reference,
            new Timestamp(1)
        );
    }

    public static Payment generatePaymentDetails() {
        return new Payment(
            TestData.Payment.etag,
            TestData.Payment.kind,
            generateLinks(),
            TestData.Payment.companyNumber,
            Collections.singletonList(generatePaymentItem())
        );
    }

    public static Links generateLinks() {
        return new Links(TestData.Payment.Links.self, TestData.Payment.Links.payment);
    }

    public static PaymentItem generatePaymentItem() {
        return new PaymentItem(
            TestData.Payment.PaymentItem.description,
            TestData.Payment.PaymentItem.descriptionIdentifier,
            TestData.Payment.PaymentItem.descriptionValues,
            TestData.Payment.PaymentItem.productType,
            TestData.Payment.PaymentItem.amount,
            TestData.Payment.PaymentItem.availablePaymentMethods,
            TestData.Payment.PaymentItem.classOfPayment,
            TestData.Payment.PaymentItem.kind,
            TestData.Payment.PaymentItem.resourceKind
        );
    }
}
