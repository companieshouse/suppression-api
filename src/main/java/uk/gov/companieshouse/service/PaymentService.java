package uk.gov.companieshouse.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.config.PaymentConfig;
import uk.gov.companieshouse.model.payment.PaymentItem;
import uk.gov.companieshouse.model.payment.Links;
import uk.gov.companieshouse.model.payment.Payment;
import java.util.Collections;

@Service
public class PaymentService {

    private static final String PAYMENT_KIND = "suppression-request#payment";
    private static final String PAYMENT_ITEM_KIND = "suppression-request#payment-details";
    private static final String PAYMENT_RESOURCE_KIND = "suppression-request#suppression-request";
    private static final String PAYMENT_DESCRIPTION = "Suppression application";
    private static final String AVAILABLE_PAYMENT_METHOD = "credit-card";
    private static final String CLASS_OF_PAYMENT = "data-maintenance";
    private static final String PRODUCT_TYPE = "sr01";

    private final PaymentConfig paymentConfig;

    public PaymentService(final PaymentConfig paymentConfig) {
        this.paymentConfig = paymentConfig;
    }

    public Payment getPaymentDetails(final String suppressionId, final String etag) {

        Payment payment = new Payment();

        payment.setEtag(etag);
        payment.setItems(Collections.singletonList(createPaymentItem()));
        payment.setKind(PAYMENT_KIND);

        Links links = new Links();
        links.setSelf("/suppressions/" + suppressionId);
        links.setPayment("/suppressions/" + suppressionId + "/payment");
        payment.setLinks(links);

        return payment;
    }

    private PaymentItem createPaymentItem() {
        PaymentItem paymentItem = new PaymentItem();
        paymentItem.setAmount(paymentConfig.getAmount());
        paymentItem.setAvailablePaymentMethods(Collections.singletonList(AVAILABLE_PAYMENT_METHOD));
        paymentItem.setClassOfPayment(Collections.singletonList(CLASS_OF_PAYMENT));
        paymentItem.setDescription(PAYMENT_DESCRIPTION);
        paymentItem.setDescriptionIdentifier(PAYMENT_DESCRIPTION);
        paymentItem.setDescriptionValues(Collections.emptyMap());
        paymentItem.setKind(PAYMENT_ITEM_KIND);
        paymentItem.setProductType(PRODUCT_TYPE);
        paymentItem.setResourceKind(PAYMENT_RESOURCE_KIND);
        return paymentItem;
    }
}
