package uk.gov.companieshouse.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.SuppressionPatchRequest;
import uk.gov.companieshouse.service.SuppressionService;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/suppressions")
public class SuppressionController {

    private final SuppressionService suppressionService;
    private final Logger logger;

    public SuppressionController(SuppressionService suppressionService, Logger logger) {
        this.suppressionService = suppressionService;
        this.logger = logger;
    }

    @Operation(summary = "Create a new suppression resource", tags = "Suppression")
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
                                                    @Valid @RequestBody final ApplicantDetails applicantDetails) {

        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {

            final String id = suppressionService.saveSuppression(applicantDetails);

            final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{applicationReference}")
                .buildAndExpand(id)
                .toUri();

            return ResponseEntity.created(location).body(id);

        } catch (Exception ex) {

            logger.error("Unable to create suppression", ex);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Operation(summary = "Partially update suppression resource by ID", tags = "Suppression")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Suppression resource updated"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorised request"),
        @ApiResponse(responseCode = "422", description = "Invalid suppression data"),
        @ApiResponse(responseCode = "404", description = "Suppression resource not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping(value = "/{suppression-id:^[A-Z0-9]{5}-[A-Z0-9]{5}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partiallyUpdateSuppression(@RequestHeader("ERIC-identity") final String userId,
                                                             @PathVariable("suppression-id") final String suppressionId,
                                                             @Valid @RequestBody final SuppressionPatchRequest suppressionPatchRequest) {

        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Optional<Suppression> suppression = suppressionService.getSuppression(suppressionId);

        if (suppression.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            suppressionService.patchSuppressionResource(suppression.get(), suppressionPatchRequest);
        } catch (Exception ex) {
            logger.error(String.format("Unable to patch suppression resource for application reference %s",
                suppression.get().getApplicationReference()), ex);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get suppression by ID", tags = "Suppression")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Suppression resource retrieved successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Suppression.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorised request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Suppression resource not found", content = @Content)
    })
    @GetMapping(value = "/{suppression-id:^[A-Z0-9]{5}-[A-Z0-9]{5}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Suppression> getSuppressionById(@RequestHeader("ERIC-identity") final String userId,
                                                          @PathVariable("suppression-id") final String suppressionId) {

        if (StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Optional<Suppression> suppression = suppressionService.getSuppression(suppressionId);

        return suppression.map(s -> ResponseEntity.status(HttpStatus.OK).body(s))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
