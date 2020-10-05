package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.service.SuppressionService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuppressionController.class)
public class SuppressionControllerTest_PATCH {

    private static final String SUPPRESSION_URI = "/suppressions/{suppression-id}";
    private static final String IDENTITY_HEADER = "ERIC-identity";
    private static final String TEST_USER_ID = "1234";
    private static final String TEST_SUPPRESSION_ID = "11111-11111";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuppressionService suppressionService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenSuppressionResourceExistsForSuppressionID_validInput_return204() throws Exception {

        final Suppression suppressionResource = getSuppressionResource();

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(suppressionResource));
        doNothing().when(suppressionService).patchSuppressionResource(any(Suppression.class), any(Suppression.class));

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(getSuppressionResource())))
            .andExpect(status().isNoContent());
    }

    @Test
    public void whenEmptyInput_return400() throws Exception {

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(""))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void whenMissingEricIdentityHeader_return400() throws Exception {

        mockMvc.perform(get(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(asJsonString(getSuppressionResource())))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void whenBlankEricIdentityHeader_return401() throws Exception {

        mockMvc.perform(get(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header(IDENTITY_HEADER, "")
            .content(asJsonString(getSuppressionResource())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenBlankSuppressionId_return404() throws Exception {

        mockMvc.perform(patch(SUPPRESSION_URI, " ")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(getSuppressionResource())))
            .andExpect(status().isNotFound());
    }

    @Test
    public void whenInvalidSuppressionIdFormat_return404() throws Exception {

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID + "-" + TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(getSuppressionResource())))
            .andExpect(status().isNotFound());
    }

    @Test
    public void whenSuppressionResourceNotFound_return404() throws Exception {

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.empty());

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(getSuppressionResource())))
            .andExpect(status().isNotFound());
    }

    @Test
    public void whenExceptionFromService_return500() throws Exception {

        final Suppression suppressionResource = getSuppressionResource();

        given(suppressionService.getSuppression(anyString())).willReturn(Optional.of(suppressionResource));
        doThrow(RuntimeException.class).when(suppressionService).patchSuppressionResource(any(Suppression.class), any(Suppression.class));

        mockMvc.perform(patch(SUPPRESSION_URI, TEST_SUPPRESSION_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(asJsonString(getSuppressionResource())))
            .andExpect(status().isInternalServerError());
    }

    private Suppression getSuppressionResource() {
        Suppression suppression = new Suppression();
        suppression.setApplicationReference(TEST_SUPPRESSION_ID);
        suppression.setCreatedAt(LocalDateTime.now());
        suppression.setEtag("I_AM_AN_ETAG");
        return suppression;
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
