package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.model.Address;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.DocumentDetails;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.service.SuppressionService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuppressionController.class)
class SuppressionControllerTest_GET {

    private static final String SUPPRESSION_URI = "/suppressions/{suppression-id}";
    private static final String TEST_SUPPRESSION_ID = "11111-11111";
    private static final String IDENTITY_HEADER = "ERIC-identity";
    private static final String TEST_USER_ID = "1234";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuppressionService suppressionService;

    @MockBean
    private Logger logger;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void whenSuppressionResourceExistsForSuppressionID_return200() throws Exception {

        final Suppression suppressionResource = getSuppressionResource();

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(suppressionResource));

        mockMvc.perform(get(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders()))
            .andExpect(status().isOk())
            .andExpect(content().json(asJsonString(suppressionResource)));
    }

    @Test
    void whenMissingEricIdentityHeader_return400() throws Exception {

        mockMvc.perform(get(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenBlankEricIdentityHeader_return401() throws Exception {

        mockMvc.perform(get(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(IDENTITY_HEADER, ""))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void whenBlankSuppressionId_return404() throws Exception {

        mockMvc.perform(get(SUPPRESSION_URI, " ")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders()))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenInvalidSuppressionIdFormat_return404() throws Exception {

        mockMvc.perform(get(SUPPRESSION_URI, TEST_SUPPRESSION_ID + "-" + TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders()))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenSuppressionResourceNotFound_return404() throws Exception {

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.empty());

        mockMvc.perform(get(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders()))
            .andExpect(status().isNotFound());
    }

    private <T> String asJsonString(T body) {
        try {
            return mapper.writeValueAsString(body);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Suppression getSuppressionResource() {
        Suppression suppression = new Suppression();
        suppression.setApplicationReference(TEST_SUPPRESSION_ID);
        suppression.setCreatedAt(LocalDateTime.now());
        suppression.setAddressToRemove(new Address());
        suppression.setApplicantDetails(new ApplicantDetails());
        suppression.setDocumentDetails(new DocumentDetails());
        suppression.setServiceAddress(new Address());
        suppression.setEtag("I_AM_AN_ETAG");
        return suppression;
    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(IDENTITY_HEADER, TEST_USER_ID);
        return httpHeaders;
    }
}
