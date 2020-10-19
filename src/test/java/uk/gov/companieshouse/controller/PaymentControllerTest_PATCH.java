package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import uk.gov.companieshouse.email_producer.EmailSendingException;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.payment.PaymentPatchRequest;
import uk.gov.companieshouse.model.payment.PaymentStatus;
import uk.gov.companieshouse.service.PaymentService;
import uk.gov.companieshouse.service.SuppressionService;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static uk.gov.companieshouse.fixtures.PaymentFixtures.generatePaymentPatchRequest;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest_PATCH {

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
    public void whenSuppressionNotFound__return404() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.empty());

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(asJsonString(paymentDetails)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void whenEmailSendFails__return500() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(getSuppression()));
        doThrow(EmailSendingException.class)
            .when(suppressionService)
            .handlePayment(any(PaymentPatchRequest.class), any(Suppression.class));

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(asJsonString(paymentDetails)))
            .andExpect(status().isInternalServerError());
    }
    
    @Test
    public void whenEmailSendSucceeds__return204() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(getSuppression()));

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(asJsonString(paymentDetails)))
            .andExpect(status().isNoContent());
    }

    private <T> String asJsonString(T body) {
        try {
            return mapper.writeValueAsString(body);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Suppression getSuppression() {
        Suppression suppression = new Suppression();
        suppression.setApplicationReference(TEST_SUPPRESSION_ID);
        suppression.setEtag(TEST_ETAG);
        return suppression;
    }
}
