package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.fixtures.SuppressionFixtures;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.service.SuppressionService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.companieshouse.TestData.Suppression.applicationReference;

@WebMvcTest(SuppressionController.class)
class SuppressionControllerTest_GET {

    private static final String SUPPRESSION_URI = "/suppressions/{suppression-id}";
    private static final String IDENTITY_HEADER = "ERIC-identity";
    private static final String TEST_USER_ID = "1234";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SuppressionService suppressionService;

    private JacksonTester<Suppression> json;

    @BeforeEach
    void setUp(){
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    void whenSuppressionResourceExistsForSuppressionID_return200() throws Exception {

        final Suppression suppressionResource =
            SuppressionFixtures.generateSuppression(applicationReference);

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(suppressionResource));

        mockMvc.perform(get(SUPPRESSION_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders()))
            .andExpect(status().isOk())
            .andExpect(content().json(this.json.write(suppressionResource).getJson()));
    }

    @Test
    void whenMissingEricIdentityHeader_return400() throws Exception {

        mockMvc.perform(get(SUPPRESSION_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenBlankEricIdentityHeader_return401() throws Exception {

        mockMvc.perform(get(SUPPRESSION_URI, applicationReference)
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

        mockMvc.perform(get(SUPPRESSION_URI,
            applicationReference + "-" + applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders()))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenSuppressionResourceNotFound_return404() throws Exception {

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.empty());

        mockMvc.perform(get(SUPPRESSION_URI, applicationReference)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders()))
            .andExpect(status().isNotFound());
    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(IDENTITY_HEADER, TEST_USER_ID);
        return httpHeaders;
    }
}
