package uk.gov.companieshouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/suppressions/healthcheck")
public class HealthcheckController {

    @Operation(summary = "Check if Suppression API is working", tags = "Health")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suppression API is up"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping()
    public ResponseEntity<String> isHealthy() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
