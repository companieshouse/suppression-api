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
import uk.gov.companieshouse.fixtures.PaymentFixtures;
import uk.gov.companieshouse.fixtures.SuppressionFixtures;
import uk.gov.companieshouse.model.payment.Payment;
import uk.gov.companieshouse.service.PaymentService;
import uk.gov.companieshouse.service.SuppressionService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.companieshouse.TestData.Suppression.applicationReference;
import static uk.gov.companieshouse.TestData.Suppression.etag;
import static uk.gov.companieshouse.TestData.Suppression.DocumentDetails.companyNumber;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest_GET {

    private static final String SUPPRESSIONS_PAYMENT_URI = "/suppressions/{suppression-id}/payment";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private SuppressionService suppressionService;

    private JacksonTester<Payment> json;

    @BeforeEach
    void setUp(){
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    void whenPaymentDetailsExistForSuppression_return200() throws Exception {

        Payment paymentDetails = PaymentFixtures.generatePaymentDetails();

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(SuppressionFixtures.generateSuppression(applicationReference)));
        given(paymentService.getPaymentDetails(applicationReference, etag, companyNumber)).willReturn(paymentDetails);

        mockMvc.perform(get(SUPPRESSIONS_PAYMENT_URI, applicationReference, companyNumber)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().json(this.json.write(paymentDetails).getJson()));
    }

    @Test
    void whenSuppressionResourceNotFound_return404() throws Exception {

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.empty());

        mockMvc.perform(get(SUPPRESSIONS_PAYMENT_URI, applicationReference, companyNumber)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNotFound());
    }
}
