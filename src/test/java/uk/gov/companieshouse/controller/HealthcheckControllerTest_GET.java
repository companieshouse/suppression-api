package uk.gov.companieshouse.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthcheckController.class)
public class HealthcheckControllerTest_GET {

    private static final String HEALTHCHECK_URI = "/suppressions/healthcheck";

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void whenHealthy_returnOK() throws Exception {
        mockMvc
            .perform(get(HEALTHCHECK_URI))
            .andExpect(status().isOk());
    }
    
}
