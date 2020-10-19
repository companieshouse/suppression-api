package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.email_producer.EmailSendingException;
import uk.gov.companieshouse.model.Address;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.DocumentDetails;
import uk.gov.companieshouse.model.PaymentDetails;
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

    private final String SUPPRESSIONS_PAYMENT_URI = "/suppressions/{suppression-id}/payment";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private SuppressionService suppressionService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void whenSuppressionExistsWithPaymentStatusPaid_return400() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        Suppression suppression = getSuppressionResource();
        suppression.setPaymentDetails(getPaymentDetails());

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(suppression));

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(asJsonString(paymentDetails)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenSuppressionNotFound__return404() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.empty());

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(asJsonString(paymentDetails)))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenEmailSendFails__return500() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(getSuppressionResource()));
        doThrow(EmailSendingException.class)
            .when(suppressionService)
            .handlePayment(any(PaymentPatchRequest.class), any(Suppression.class));

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(asJsonString(paymentDetails)))
            .andExpect(status().isInternalServerError());
    }
    
    @Test
    void whenEmailSendSucceeds__return204() throws Exception {

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(getSuppressionResource()));

        mockMvc.perform(patch(SUPPRESSIONS_PAYMENT_URI, applicationReference)
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

    private Suppression getSuppressionResource() {
        return new Suppression(TestData.Suppression.createdAt,
            applicationReference,
            getApplicantDetails(),
            getAddress(),
            getAddress(),
            getDocumentDetails(),
            getAddress(),
            TestData.Suppression.etag,
            null);
    }

    private Address getAddress() {
        return new Address(TestData.Suppression.Address.line1,
            TestData.Suppression.Address.line2,
            TestData.Suppression.Address.town,
            TestData.Suppression.Address.county,
            TestData.Suppression.Address.country,
            TestData.Suppression.Address.postcode);
    }

    private ApplicantDetails getApplicantDetails() {
        return new ApplicantDetails(TestData.Suppression.ApplicantDetails.fullName,
            TestData.Suppression.ApplicantDetails.previousName,
            TestData.Suppression.ApplicantDetails.emailAddress,
            TestData.Suppression.ApplicantDetails.dateOfBirth);
    }

    private DocumentDetails getDocumentDetails() {
        return new DocumentDetails(TestData.Suppression.DocumentDetails.companyName,
            TestData.Suppression.DocumentDetails.companyNumber,
            TestData.Suppression.DocumentDetails.description,
            TestData.Suppression.DocumentDetails.date);
    }

    private PaymentDetails getPaymentDetails() {
        return new PaymentDetails(TestData.Suppression.PaymentDetails.reference,
            TestData.Suppression.PaymentDetails.paidAt,
            TestData.Suppression.PaymentDetails.status);
    }
}
