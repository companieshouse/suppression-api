package uk.gov.companieshouse.controller;

import org.apache.commons.lang3.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.service.SuppressionService;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/suppressions")
public class SuppressionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuppressionController.class);

    private final SuppressionService suppressionService;

    public SuppressionController(SuppressionService suppressionService){ this.suppressionService = suppressionService; }

    @Operation(summary = "Create a new suppression", tags = "suppression")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Suppression resource created", headers = {
            @Header(name = "location")
        }),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorised request"),
        @ApiResponse(responseCode = "422", description = "Invalid suppression data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitSuppression(@RequestHeader("ERIC-identity") String userId,
                                                    @Valid @RequestBody final Suppression suppression) {

        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LOGGER.info("POST /suppressions with application reference {}", suppression.getApplicationReference());

        try {

            final String id = suppressionService.saveSuppression(suppression);

            final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{applicationReference}")
                .buildAndExpand(id)
                .toUri();

            return ResponseEntity.created(location).body(id);

        } catch (Exception ex) {

            LOGGER.error("Unable to create suppression for application reference {}", suppression.getApplicationReference(), ex);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

}
