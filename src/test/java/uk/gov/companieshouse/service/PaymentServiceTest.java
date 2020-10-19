package uk.gov.companieshouse.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import uk.gov.companieshouse.config.PaymentConfig;
import uk.gov.companieshouse.model.payment.PaymentItem;
import uk.gov.companieshouse.model.payment.Links;
import uk.gov.companieshouse.model.payment.Payment;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@TestPropertySource
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    private static final String TEST_SUPPRESSION_ID = "123";
    private static final String TEST_COMPANY_NUMBER = "SC123123";
    private static final String PAYMENT_KIND = "suppression-request#payment";
    private static final String PAYMENT_ITEM_KIND = "suppression-request#payment-details";
    private static final String PAYMENT_RESOURCE_KIND = "suppression-request#suppression-request";
    private static final String PAYMENT_DESCRIPTION = "Suppression application";
    private static final String PAYMENT_AMOUNT = "32";
    private static final String AVAILABLE_PAYMENT_METHOD = "credit-card";
    private static final String CLASS_OF_PAYMENT = "data-maintenance";
    private static final String PRODUCT_TYPE = "sr01";

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentConfig paymentConfig;

    @Test
    public void testGetPaymentDetails_returnsPaymentDetails() {

        when(paymentConfig.getAmount()).thenReturn(PAYMENT_AMOUNT);
        
        Payment payment = paymentService.getPaymentDetails(TEST_SUPPRESSION_ID, "1", TEST_COMPANY_NUMBER);

        assertNotNull(payment.getEtag());
        assertEquals(PAYMENT_KIND, payment.getKind());

        PaymentItem paymentItem = payment.getItems().get(0);
        
        assertEquals(PAYMENT_AMOUNT, paymentItem.getAmount());
        assertEquals(AVAILABLE_PAYMENT_METHOD, paymentItem.getAvailablePaymentMethods().get(0));
        assertEquals(CLASS_OF_PAYMENT, paymentItem.getClassOfPayment().get(0));
        assertEquals(PAYMENT_DESCRIPTION, paymentItem.getDescription());
        assertEquals(PAYMENT_DESCRIPTION, paymentItem.getDescriptionIdentifier());
        assertEquals(Collections.emptyMap(), paymentItem.getDescriptionValues());
        assertEquals(PAYMENT_ITEM_KIND, paymentItem.getKind());
        assertEquals(PRODUCT_TYPE, paymentItem.getProductType());
        assertEquals(PAYMENT_RESOURCE_KIND, paymentItem.getResourceKind());

        Links links = payment.getLinks();
        assertEquals("/suppressions/" + TEST_SUPPRESSION_ID + "/payment", links.getPayment());
        assertEquals("/suppressions/" + TEST_SUPPRESSION_ID, links.getSelf());
    }
}
