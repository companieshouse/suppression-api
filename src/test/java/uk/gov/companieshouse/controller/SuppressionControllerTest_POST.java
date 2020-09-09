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
    private static final String TEST_RESOURCE_ID = "1";

    @MockBean
    private SuppressionService suppressionService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        when(suppressionService.saveSuppression(any(Suppression.class))).thenReturn(TEST_RESOURCE_ID);
    }

    @Test
    public void whenValidInput_return201() throws Exception {

        final String validSuppression = asJsonString("src/test/resources/data/validSuppression.json");

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(validSuppression))
            .andExpect(status().isCreated())
            .andExpect(content().json(TEST_RESOURCE_ID))
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/suppressions/"
                + TEST_RESOURCE_ID));
    }

    @Test
    public void whenInvalidInput_return422() throws Exception {

        final String invalidSuppression = asJsonString("src/test/resources/data/invalidSuppression.json");

        System.out.println(invalidSuppression);

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(invalidSuppression))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                content().json("{\"documentDetails.companyNumber\":\"companyNumber must not be blank\",\"addressToRemove\":\"addressToRemove must not be null\"}")
            );
    }

    @Test
    public void whenEmptyInput_return400() throws Exception {

        mockMvc.perform(post(SUPPRESSION_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .headers(createHttpHeaders())
            .content(""))
            .andExpect(status().isBadRequest());
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

    private HttpHeaders createHttpHeaders() {
        return new HttpHeaders();
    }
}
