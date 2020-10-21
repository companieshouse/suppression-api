package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.fixtures.SuppressionFixtures;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.model.Address;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.SuppressionPatchRequest;
import uk.gov.companieshouse.service.SuppressionService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.companieshouse.TestData.Suppression.applicationReference;

@WebMvcTest(SuppressionController.class)
class SuppressionControllerTest_PATCH {

    private static final String SUPPRESSION_URI = "/suppressions/{suppression-id}";
    private static final String IDENTITY_HEADER = "ERIC-identity";
    private static final String TEST_USER_ID = "1234";
    private static final String TEST_SUPPRESSION_ID = "11111-11111";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuppressionService suppressionService;

    @MockBean
    private Logger logger;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void whenPartiallyUpdateSuppression_applicantDetails_return204() throws Exception {

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(SuppressionFixtures.generateSuppression(applicationReference)));
        doNothing().when(suppressionService).patchSuppressionResource(any(Suppression.class), any(SuppressionPatchRequest.class));

        final Suppression updateSuppressionRequest = new Suppression();
        updateSuppressionRequest.setApplicantDetails(SuppressionFixtures.generateApplicantDetails());

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(updateSuppressionRequest)))
            .andExpect(status().isNoContent());
    }

    @Test
    void whenPartiallyUpdateSuppression_addressToRemove_return204() throws Exception {

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(SuppressionFixtures.generateSuppression(applicationReference)));
        doNothing().when(suppressionService).patchSuppressionResource(any(Suppression.class), any(SuppressionPatchRequest.class));

        final Suppression updateSuppressionRequest = new Suppression();
        updateSuppressionRequest.setAddressToRemove(SuppressionFixtures.generateAddress());

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(updateSuppressionRequest)))
            .andExpect(status().isNoContent());
    }

    @Test
    void whenPartiallyUpdateSuppression_serviceAddress_return204() throws Exception {

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(SuppressionFixtures.generateSuppression(applicationReference)));
        doNothing().when(suppressionService).patchSuppressionResource(any(Suppression.class), any(SuppressionPatchRequest.class));

        final Suppression updateSuppressionRequest = new Suppression();
        updateSuppressionRequest.setServiceAddress(SuppressionFixtures.generateAddress());

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(updateSuppressionRequest)))
            .andExpect(status().isNoContent());
    }

    @Test
    void whenPartiallyUpdateSuppression_documentDetails_return204() throws Exception {

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(SuppressionFixtures.generateSuppression(applicationReference)));
        doNothing().when(suppressionService).patchSuppressionResource(any(Suppression.class), any(SuppressionPatchRequest.class));

        final Suppression updateSuppressionRequest = new Suppression();
        updateSuppressionRequest.setDocumentDetails(SuppressionFixtures.generateDocumentDetails());

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(updateSuppressionRequest)))
            .andExpect(status().isNoContent());
    }

    @Test
    void whenPartiallyUpdateSuppression_contactAddress_return204() throws Exception {

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(SuppressionFixtures.generateSuppression(applicationReference)));
        doNothing().when(suppressionService).patchSuppressionResource(any(Suppression.class), any(SuppressionPatchRequest.class));

        final Suppression updateSuppressionRequest = new Suppression();
        updateSuppressionRequest.setContactAddress(SuppressionFixtures.generateAddress());

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(updateSuppressionRequest)))
            .andExpect(status().isNoContent());
    }

    @Test
    void whenMissingRequestBody_return400() throws Exception {

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenMissingEricIdentityHeader_return400() throws Exception {

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(asJsonString(SuppressionFixtures.generateSuppression(applicationReference))))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenBlankEricIdentityHeader_return401() throws Exception {

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(IDENTITY_HEADER, " ")
            .content(asJsonString(SuppressionFixtures.generateSuppression(applicationReference))))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void whenBlankSuppressionId_return404() throws Exception {

        mockMvc.perform(patch(SUPPRESSION_URI, " ")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(SuppressionFixtures.generateSuppression(applicationReference))))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenInvalidSuppressionIdFormat_return404() throws Exception {

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID + "-" + TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(SuppressionFixtures.generateSuppression(applicationReference))))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenSuppressionResourceNotFound_return404() throws Exception {

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.empty());

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(SuppressionFixtures.generateSuppression(applicationReference))))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenPartiallyUpdateSuppression_applicantDetails_invalid_return422() throws Exception {

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(SuppressionFixtures.generateSuppression(applicationReference)));
        doNothing().when(suppressionService).patchSuppressionResource(any(Suppression.class), any(SuppressionPatchRequest.class));

        final Suppression updateSuppressionRequest = new Suppression();
        updateSuppressionRequest.setApplicantDetails(new ApplicantDetails(TestData.Suppression.ApplicantDetails.fullName,
            TestData.Suppression.ApplicantDetails.previousName,
            null,
            " "));

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(updateSuppressionRequest)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void whenPartiallyUpdateSuppression_addressToRemove_invalid_return422() throws Exception {

        given(suppressionService.getSuppression(anyString()))
            .willReturn(Optional.of(SuppressionFixtures.generateSuppression(applicationReference)));
        doNothing().when(suppressionService).patchSuppressionResource(any(Suppression.class),
            any(SuppressionPatchRequest.class));

        final Suppression updateSuppressionRequest = new Suppression();
        updateSuppressionRequest.setAddressToRemove(new Address(null,
            TestData.Suppression.Address.line2,
            TestData.Suppression.Address.town,
            TestData.Suppression.Address.county,
            TestData.Suppression.Address.country,
            " "));

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(updateSuppressionRequest)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void whenExceptionFromService_return500() throws Exception {

        final Suppression suppressionResource = SuppressionFixtures.generateSuppression(applicationReference);

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(suppressionResource));
        doThrow(RuntimeException.class).when(suppressionService).patchSuppressionResource(any(Suppression.class),
            any(SuppressionPatchRequest.class));

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(suppressionResource)))
            .andExpect(status().isInternalServerError());
    }

    private <T> String asJsonString(T body) {
        try {
            return mapper.writeValueAsString(body);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(IDENTITY_HEADER, TEST_USER_ID);
        return httpHeaders;
    }
}
