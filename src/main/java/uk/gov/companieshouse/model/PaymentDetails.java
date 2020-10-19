package uk.gov.companieshouse.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.gov.companieshouse.model.payment.PaymentStatus;

import java.sql.Timestamp;

public class PaymentDetails {

    private String reference;
    private Timestamp paidAt;
    private PaymentStatus status;

    public PaymentDetails() {
        this(null, null, null);
    }

    public PaymentDetails(String reference, Timestamp paidAt, PaymentStatus status) {
        this.reference = reference;
        this.paidAt = paidAt;
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Timestamp getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Timestamp paidAt) {
        this.paidAt = paidAt;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PaymentDetails that = (PaymentDetails) o;

        return new EqualsBuilder()
            .append(reference, that.reference)
            .append(paidAt, that.paidAt)
            .append(status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(reference)
            .append(paidAt)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("reference", reference)
            .append("paidAt", paidAt)
            .append("status", status)
            .toString();
    }
}
