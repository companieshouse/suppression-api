package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import uk.gov.companieshouse.email_producer.EmailSendingException;
import uk.gov.companieshouse.fixtures.SuppressionFixtures;
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

import static uk.gov.companieshouse.TestData.Suppression.applicationReference;
import static uk.gov.companieshouse.fixtures.PaymentFixtures.generatePaymentPatchRequest;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest_PATCH {

    private static final String SUPPRESSIONS_PAYMENT_URI = "/suppressions/{suppression-id}/payment";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private SuppressionService suppressionService;

    private JacksonTester<PaymentPatchRequest> json;

    @BeforeEach
    void setUp(){
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    void whenSuppressionExistsWithPaymentStatusPaid_return400() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setPaymentDetails(SuppressionFixtures.generatePaymentDetails());

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(suppression));

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.json.write(paymentDetails).getJson()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenSuppressionNotFound__return404() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.empty());

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.json.write(paymentDetails).getJson()))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenEmailSendFails__return500() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setPaymentDetails(null);

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(suppression));
        doThrow(EmailSendingException.class)
            .when(suppressionService)
            .handlePayment(any(PaymentPatchRequest.class), any(Suppression.class));

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.json.write(paymentDetails).getJson()))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void whenEmailSendSucceeds__return204() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setPaymentDetails(null);

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(suppression));

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.json.write(paymentDetails).getJson()))
            .andExpect(status().isNoContent());
    }
}
