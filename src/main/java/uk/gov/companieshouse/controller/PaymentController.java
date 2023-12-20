package uk.gov.companieshouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.gov.companieshouse.model.PaymentDetails;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.payment.Payment;
import uk.gov.companieshouse.model.payment.PaymentPatchRequest;
import uk.gov.companieshouse.model.payment.PaymentStatus;
import uk.gov.companieshouse.service.PaymentService;
import uk.gov.companieshouse.service.SuppressionService;
import uk.gov.companieshouse.email_producer.EmailSendingException;
import uk.gov.companieshouse.logging.Logger;

@RestController
@RequestMapping("/suppressions/{suppression-id}/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final SuppressionService suppressionService;
    private final Logger logger;

    public PaymentController(PaymentService paymentService, SuppressionService suppressionService, Logger logger) {
        this.paymentService = paymentService;
        this.suppressionService = suppressionService;
        this.logger = logger;
    }

    @Operation(summary = "Get suppression payment details by ID", tags = "Payment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Suppression payment details retrieved successfully",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)) }),
        @ApiResponse(responseCode = "404", description = "Suppression payment details not found", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> getPaymentDetails(@PathVariable("suppression-id") final String suppressionId) {

        final Optional<Suppression> suppression = suppressionService.getSuppression(suppressionId);

        if (suppression.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        final String etag = suppression.get().getEtag();
        final String companyNumber = suppression.get().getDocumentDetails().getCompanyNumber();
        final String applicationReference = suppression.get().getApplicationReference();
        
        final Payment paymentDetails = paymentService.getPaymentDetails(applicationReference, etag, companyNumber);

        return ResponseEntity.status(HttpStatus.OK).body(paymentDetails);
    }

    @Operation(summary = "Update suppression payment details by ID", tags = "Payment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Suppression payment details updated successfully"),
        @ApiResponse(responseCode = "400", description = "Unable to update payment details, status is paid"),
        @ApiResponse(responseCode = "404", description = "Suppression application not found"),
        @ApiResponse(responseCode = "500", description = "Failed to send email")
    })
    @PatchMapping
    public ResponseEntity<Void> patchPaymentDetails(
        @PathVariable("suppression-id") final String suppressionId,
        @Valid @RequestBody final PaymentPatchRequest paymentPatchRequest ) {

        final Optional<Suppression> suppression = suppressionService.getSuppression(suppressionId);

        if (suppression.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        final Suppression suppressionResource = suppression.get();
        final PaymentDetails paymentDetails = suppressionResource.getPaymentDetails();

        if (paymentDetails != null && paymentDetails.getStatus() == PaymentStatus.PAID) {

            logger.error(String.format("Unable to update payment details for suppression application ref. %s, payment " +
                    "status is %s (payment ref. %s)", suppressionResource.getApplicationReference(), PaymentStatus.PAID,
                paymentPatchRequest.getPaymentReference()));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            suppressionService.handlePayment(paymentPatchRequest, suppressionResource);
        } catch (EmailSendingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
