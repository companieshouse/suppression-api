package uk.gov.companieshouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.companieshouse.model.payment.Payment;
import uk.gov.companieshouse.service.PaymentService;

@RestController
@RequestMapping("/suppressions/{suppression-id}/payment")
public class PaymentController {
    
    private final PaymentService paymentService;
    
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Get suppression payment details by ID", tags = "Payment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Suppression payment details retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Suppression payment details not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> getPaymentDetails(@PathVariable("suppression-id") final String suppressionId) {

        // get suppression from mongodb, return 404 if does not exist
        
        Payment payment = paymentService.getPaymentDetails(suppressionId);
        
        return ResponseEntity.status(HttpStatus.OK).body(payment);
    }
}
