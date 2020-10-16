package uk.gov.companieshouse.model.payment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {
    PAID("paid"),
    CANCELLED("cancelled"),
    FAILED("failed");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
