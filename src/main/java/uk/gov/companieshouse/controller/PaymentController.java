package uk.gov.companieshouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.payment.Payment;
import uk.gov.companieshouse.service.PaymentService;
import uk.gov.companieshouse.service.SuppressionService;

import java.util.Optional;

@RestController
@RequestMapping("/suppressions/{suppression-id}/payment")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
    
    private final PaymentService paymentService;
    private final SuppressionService suppressionService;
    
    public PaymentController(PaymentService paymentService, SuppressionService suppressionService) {
        this.paymentService = paymentService;
        this.suppressionService = suppressionService;
    }

    @Operation(summary = "Get suppression payment details by ID", tags = "Payment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Suppression payment details retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Suppression payment details not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> getPaymentDetails(@PathVariable("suppression-id") final String suppressionId) {

        LOGGER.info("GET /suppressions/{}/payment", suppressionId);
        
        final Optional<Suppression> suppression = suppressionService.getSuppression(suppressionId);
        if (suppression.isEmpty()) {
            LOGGER.error("Suppression resource not found for reference {}", suppressionId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        final String etag = suppression.get().getEtag();
        
        final Payment paymentDetails = paymentService.getPaymentDetails(suppressionId, etag);
        
        return ResponseEntity.status(HttpStatus.OK).body(paymentDetails);
    }
}
