package uk.gov.companieshouse.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.model.payment.Item;
import uk.gov.companieshouse.model.payment.Links;
import uk.gov.companieshouse.model.payment.Payment;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    private static final String TEST_SUPPRESSION_ID = "123";
    private static final String PAYMENT_KIND = "suppression-request#payment";
    private static final String PAYMENT_ITEM_KIND = "suppression-request#payment-details";
    private static final String PAYMENT_RESOURCE_KIND = "suppression-request#suppression-request";
    private static final String PAYMENT_DESCRIPTION = "Suppression application";
    private static final String PAYMENT_AMOUNT = "32";
    private static final String AVAILABLE_PAYMENT_METHOD = "credit-card";
    private static final String CLASS_OF_PAYMENT = "data-maintenance";

    @Test
    public void testGetPaymentDetails_returnsPaymentDetails() {

        Payment payment = paymentService.getPaymentDetails(TEST_SUPPRESSION_ID);

        assertEquals("", payment.getEtag());
        assertEquals(PAYMENT_KIND, payment.getKind());

        Item item = payment.getItems().get(0);
        assertEquals(PAYMENT_AMOUNT, item.getAmount());
        assertEquals(AVAILABLE_PAYMENT_METHOD, item.getAvailablePaymentMethods().get(0));
        assertEquals(CLASS_OF_PAYMENT, item.getClassOfPayment().get(0));
        assertEquals(PAYMENT_DESCRIPTION, item.getDescription());
        assertEquals(PAYMENT_DESCRIPTION, item.getDescriptionIdentifier());
        assertEquals(Collections.emptyMap(), item.getDescriptionValues());
        assertEquals(PAYMENT_ITEM_KIND, item.getKind());
        assertEquals(PAYMENT_DESCRIPTION, item.getProductType());
        assertEquals(PAYMENT_RESOURCE_KIND, item.getResourceKind());

        Links links = payment.getLinks();
        assertEquals("/suppressions/" + TEST_SUPPRESSION_ID + "/payment", links.getPayment());
        assertEquals("/suppressions/" + TEST_SUPPRESSION_ID, links.getSelf());
    }
}
