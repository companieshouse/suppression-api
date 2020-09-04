package uk.gov.companieshouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.service.SuppressionService;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class SuppressionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuppressionController.class);

    private final SuppressionService suppressionService;

    public SuppressionController(SuppressionService suppressionService){ this.suppressionService = suppressionService; }

    @Operation(summary = "Create a new suppression", tags = "Suppression")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Appeal resource created", headers = {
            @Header(name = "location")
        }),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorised request"),
        @ApiResponse(responseCode = "422", description = "Invalid appeal data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/suppressions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitSuppression(@Valid @RequestBody final Suppression suppression){


        try {
            final String id = suppressionService.saveSuppression(suppression);

            final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

            return ResponseEntity.created(location).body(id);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
