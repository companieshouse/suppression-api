package uk.gov.companieshouse.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.sql.Timestamp;

public class PaymentPatchRequest {
    
    private PaymentStatus status;

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("paid_at")
    private Timestamp paidAt;

    public PaymentPatchRequest() {
        this(null, null, null);
    }

    public PaymentPatchRequest(PaymentStatus status, String paymentReference, Timestamp paidAt){
        this.status = status;
        this.paymentReference = paymentReference;
        this.paidAt = paidAt;
    }

    public PaymentStatus getStatus() { return status; }

    public String getPaymentReference() { return paymentReference; }

    public Timestamp getPaidAt() { return paidAt; }

    public void setStatus(PaymentStatus status) { this.status = status; }

    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

    public void setPaidAt(Timestamp paidAt) { this.paidAt = paidAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PaymentPatchRequest that = (PaymentPatchRequest) o;

        return new EqualsBuilder()
            .append(status, that.status)
            .append(paymentReference, that.paymentReference)
            .append(paidAt, that.paidAt)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(status)
            .append(paymentReference)
            .append(paidAt)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("status", status)
            .append("paymentReference", paymentReference)
            .append("paidAt", paidAt)
            .toString();
    }
}
