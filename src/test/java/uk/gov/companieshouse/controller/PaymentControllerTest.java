package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.model.payment.Item;
import uk.gov.companieshouse.model.payment.Links;
import uk.gov.companieshouse.model.payment.Payment;
import uk.gov.companieshouse.service.PaymentService;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    private final String SUPPRESSIONS_PAYMENT_URI = "/suppressions/{suppression-id}/payment";
    private final String TEST_SUPPRESSION_ID = "123";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenPaymentDetailsExistForSuppression_return200() throws Exception {

        Payment paymentDetails = getPaymentDetails();

        given(paymentService.getPaymentDetails(TEST_SUPPRESSION_ID)).willReturn(paymentDetails);

        mockMvc.perform(get(SUPPRESSIONS_PAYMENT_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().json(asJsonString(paymentDetails)));
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

        Item item = new Item();
        item.setDescription("Suppression application");
        item.setDescriptionIdentifier("Suppression application");
        item.setDescriptionValues(Collections.emptyMap());
        item.setProductType("Suppression application");
        item.setAmount("32");
        item.setAvailablePaymentMethods(Collections.singletonList("credit-card"));
        item.setClassOfPayment(Collections.singletonList("data-maintenance"));
        item.setKind("suppression-request#payment-details");
        item.setResourceKind("suppression-request#suppression-request");

        payment.setEtag("I_AM_AN_ETAG");
        payment.setKind("suppression-request#payment");
        payment.setLinks(links);
        payment.setItems(Collections.singletonList(item));
        return payment;
    }
}
