package uk.gov.companieshouse.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.fixtures.SuppressionFixtures;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.service.SuppressionService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static uk.gov.companieshouse.JsonConverter.convertObjectToJsonString;
import static uk.gov.companieshouse.TestData.Suppression.applicationReference;

@WebMvcTest(SuppressionController.class)
class SuppressionControllerTest_POST {

    private static final String SUPPRESSION_URI = "/suppressions";
    private static final String IDENTITY_HEADER = "ERIC-identity";
    private static final String TEST_USER_ID = "1234";

    @MockBean
    private SuppressionService suppressionService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        when(suppressionService.saveSuppression(any(ApplicantDetails.class))).thenReturn(applicationReference);
    }

    @Test
    void whenValidInput_return201() throws Exception {

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(convertObjectToJsonString(SuppressionFixtures.generateApplicantDetails())))
            .andExpect(status().isCreated())
            .andExpect(content().string(applicationReference))
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/suppressions/" + applicationReference));
    }

    @Test
    void whenEmptyInput_return400() throws Exception {

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(""))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidHeader_return401() throws Exception {

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(" "))
            .content(convertObjectToJsonString(SuppressionFixtures.generateApplicantDetails())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void whenInvalidInput_return422() throws Exception {

        ApplicantDetails invalid = SuppressionFixtures.generateApplicantDetails();
        invalid.setDateOfBirth(null);

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(convertObjectToJsonString(invalid)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                content().json("{\"dateOfBirth\":\"date of birth must not be blank\"}")
            );
    }

    @Test
    void whenExceptionFromService_return500() throws Exception {

        when(suppressionService.saveSuppression(any(ApplicantDetails.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(convertObjectToJsonString(SuppressionFixtures.generateApplicantDetails())))
            .andExpect(status().isInternalServerError());
    }

    private HttpHeaders createHttpHeaders(String testUserId) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(IDENTITY_HEADER, testUserId);
        return httpHeaders;
    }
}
