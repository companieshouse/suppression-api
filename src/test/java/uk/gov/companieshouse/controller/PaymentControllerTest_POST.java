package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.payment.PaymentItem;
import uk.gov.companieshouse.model.payment.Links;
import uk.gov.companieshouse.model.payment.Payment;
import uk.gov.companieshouse.service.PaymentService;
import uk.gov.companieshouse.service.SuppressionService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest_POST {

    private final String SUPPRESSIONS_PAYMENT_URI = "/suppressions/{suppression-id}/payment";
    private final String TEST_SUPPRESSION_ID = "123";
    private final String TEST_ETAG = "I_AM_AN_ETAG";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;
    
    @MockBean
    private SuppressionService suppressionService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenPaymentDetailsExistForSuppression_return200() throws Exception {

        Payment paymentDetails = getPaymentDetails();

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(getSuppression()));
        given(paymentService.getPaymentDetails(TEST_SUPPRESSION_ID, TEST_ETAG)).willReturn(paymentDetails);

        String jsonContent = asJsonString(paymentDetails);

        mockMvc.perform(get(SUPPRESSIONS_PAYMENT_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().json(jsonContent));
    }

    @Test
    public void whenSuppressionResourceNotFound_return404() throws Exception {

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.empty());

        mockMvc.perform(get(SUPPRESSIONS_PAYMENT_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNotFound());
    }

    private <T> String asJsonString(T body) {
        try {
            return mapper.writeValueAsString(body);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Payment getPaymentDetails() {
        Payment payment = new Payment();

        Links links = new Links();
        links.setSelf("/suppressions/" + TEST_SUPPRESSION_ID);
        links.setPayment("/suppressions/" + TEST_SUPPRESSION_ID + "/payment");

        PaymentItem paymentItem = new PaymentItem();
        paymentItem.setDescription("Suppression application");
        paymentItem.setDescriptionIdentifier("Suppression application");
        paymentItem.setDescriptionValues(Collections.emptyMap());
        paymentItem.setProductType("Suppression application");
        paymentItem.setAmount("32");
        paymentItem.setAvailablePaymentMethods(Collections.singletonList("credit-card"));
        paymentItem.setClassOfPayment(Collections.singletonList("data-maintenance"));
        paymentItem.setKind("suppression-request#payment-details");
        paymentItem.setResourceKind("suppression-request#suppression-request");

        payment.setEtag(TEST_ETAG);
        payment.setKind("suppression-request#payment");
        payment.setLinks(links);
        payment.setPaymentItems(Collections.singletonList(paymentItem));
        return payment;
    }

    private Suppression getSuppression() {
        Suppression suppression = new Suppression();
        suppression.setEtag(TEST_ETAG);
        return suppression;
    }
}
