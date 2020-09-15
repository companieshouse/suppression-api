package uk.gov.companieshouse.service;

import org.springframework.stereotype.Service;
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
    private static final String PAYMENT_AMOUNT = "32";
    private static final String AVAILABLE_PAYMENT_METHOD = "credit-card";
    private static final String CLASS_OF_PAYMENT = "data-maintenance";

    public Payment getPaymentDetails(String suppressionId, String etag) {

        Payment payment = new Payment();
        
        payment.setEtag(etag);
        payment.setPaymentItems(Collections.singletonList(createPaymentItem()));
        payment.setKind(PAYMENT_KIND);

        Links links = new Links();
        links.setSelf("/suppressions/" + suppressionId);
        links.setPayment("/suppressions/" + suppressionId + "/payment");
        payment.setLinks(links);

        return payment;
    }

    private PaymentItem createPaymentItem() {
        PaymentItem paymentItem = new PaymentItem();
        paymentItem.setAmount(PAYMENT_AMOUNT);
        paymentItem.setAvailablePaymentMethods(Collections.singletonList(AVAILABLE_PAYMENT_METHOD));
        paymentItem.setClassOfPayment(Collections.singletonList(CLASS_OF_PAYMENT));
        paymentItem.setDescription(PAYMENT_DESCRIPTION);
        paymentItem.setDescriptionIdentifier(PAYMENT_DESCRIPTION);
        paymentItem.setDescriptionValues(Collections.emptyMap());
        paymentItem.setKind(PAYMENT_ITEM_KIND);
        paymentItem.setProductType(PAYMENT_DESCRIPTION);
        paymentItem.setResourceKind(PAYMENT_RESOURCE_KIND);
        return paymentItem;
    }
}
