package uk.gov.companieshouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.service.SuppressionService;

import java.io.File;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(SuppressionController.class)
public class SuppressionControllerTest_POST {

    private static final String SUPPRESSION_URI = "/suppressions";
    private static final String IDENTITY_HEADER = "ERIC-identity";
    private static final String TEST_USER_ID = "1234";
    private static final String TEST_RESOURCE_ID = "11111-11111";

    @MockBean
    private SuppressionService suppressionService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private String validSuppression;

    @BeforeEach
    public void setUp() {

        when(suppressionService.saveSuppression(any(Suppression.class))).thenReturn(TEST_RESOURCE_ID);

        validSuppression = asJsonString("src/test/resources/data/validSuppression_complete.json");
    }

    @Test
    public void whenValidInput_return201() throws Exception {

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(validSuppression))
            .andExpect(status().isCreated())
            .andExpect(content().string(TEST_RESOURCE_ID))
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/suppressions/" + TEST_RESOURCE_ID));
    }

    @Test
    public void whenEmptyInput_return400() throws Exception {

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(""))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void whenInvalidHeader_return401() throws Exception {

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(" "))
            .content(validSuppression))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenInvalidInput_return422() throws Exception {

        final String invalidSuppression = asJsonString("src/test/resources/data/invalidSuppression_missingFields.json");

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(invalidSuppression))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                content().json("{\"applicantDetails.dateOfBirth\":\"date of birth must not be blank\"}")
            );
    }

    @Test
    public void whenEmptyReference_return201() throws Exception {

        final String validSuppression = asJsonString("src/test/resources/data/validSuppression_emptyReference.json");

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(validSuppression))
            .andExpect(status().isCreated());
    }

    @Test
    public void whenExceptionFromService_return500() throws Exception {

        when(suppressionService.saveSuppression(any(Suppression.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders(TEST_USER_ID))
            .content(validSuppression))
            .andExpect(status().isInternalServerError());
    }


    private String asJsonString(final String pathname, final Function<Suppression, Suppression> suppressionModifier) {
        try {
            final Suppression suppression = mapper.readValue(new File(pathname), Suppression.class);
            return new ObjectMapper().writeValueAsString(suppressionModifier.apply(suppression));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String asJsonString(final String pathname) {
        return asJsonString(pathname, Function.identity());
    }

    private HttpHeaders createHttpHeaders(String testUserId) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(IDENTITY_HEADER,testUserId);
        return httpHeaders;
    }
}
