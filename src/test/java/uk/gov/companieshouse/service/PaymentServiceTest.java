package uk.gov.companieshouse.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.config.PaymentConfig;
import uk.gov.companieshouse.model.payment.PaymentItem;
import uk.gov.companieshouse.model.payment.Links;
import uk.gov.companieshouse.model.payment.Payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static uk.gov.companieshouse.TestData.Payment.Links.self;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.amount;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.availablePaymentMethods;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.classOfPayment;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.description;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.descriptionIdentifier;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.descriptionValues;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.kind;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.productType;
import static uk.gov.companieshouse.TestData.Payment.PaymentItem.resourceKind;
import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.companyNumber;
import static uk.gov.companieshouse.TestData.Suppression.applicationReference;
import static uk.gov.companieshouse.TestData.Suppression.etag;

@TestPropertySource
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentConfig paymentConfig;

    @Test
    void testGetPaymentDetails_returnsPaymentDetails() {

        when(paymentConfig.getAmount()).thenReturn(amount);
        
        Payment payment = paymentService.getPaymentDetails(applicationReference, etag, companyNumber);

        assertNotNull(payment.getEtag());
        assertEquals(TestData.Payment.kind, payment.getKind());

        PaymentItem paymentItem = payment.getItems().get(0);
        
        assertEquals(amount, paymentItem.getAmount());
        assertEquals(availablePaymentMethods, paymentItem.getAvailablePaymentMethods());
        assertEquals(classOfPayment, paymentItem.getClassOfPayment());
        assertEquals(description, paymentItem.getDescription());
        assertEquals(descriptionIdentifier, paymentItem.getDescriptionIdentifier());
        assertEquals(descriptionValues, paymentItem.getDescriptionValues());
        assertEquals(kind, paymentItem.getKind());
        assertEquals(productType, paymentItem.getProductType());
        assertEquals(resourceKind, paymentItem.getResourceKind());

        Links links = payment.getLinks();
        assertEquals(TestData.Payment.Links.payment, links.getPayment());
        assertEquals(self, links.getSelf());
    }
}
