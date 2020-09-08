package uk.gov.companieshouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.service.SuppressionService;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SuppressionController {

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
    @PostMapping(value = "/suppressions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitSuppression(@Valid @RequestBody final Suppression suppression) {


        if (suppression.getApplicationReference().isEmpty()) {

            String generatedReference = suppressionService.generateUniqueSuppressionReference();
            suppression.setApplicationReference(generatedReference);

        } else if (suppressionService.isExistingSuppressionID(suppression.getApplicationReference())) {

            suppressionService.deleteSuppressionByReference(suppression.getApplicationReference());

        }

        try {

            final String id = suppressionService.saveSuppression(suppression);

            final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{applicationReference}")
                .buildAndExpand(id)
                .toUri();

            return ResponseEntity.created(location).body(id);

        } catch (Exception ex) {

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
