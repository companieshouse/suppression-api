package uk.gov.companieshouse.database.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.AccessType;
import uk.gov.companieshouse.model.payment.PaymentStatus;

import java.io.Serializable;
import java.sql.Timestamp;

@AccessType(AccessType.Type.PROPERTY)
public class PaymentDetailsEntity implements Serializable {

    private final String reference;
    private final Timestamp paidAt;
    private final PaymentStatus status;

    public PaymentDetailsEntity(String reference, Timestamp paidAt, PaymentStatus status) {
        this.reference = reference;
        this.paidAt = paidAt;
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public Timestamp getPaidAt() {
        return paidAt;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PaymentDetailsEntity that = (PaymentDetailsEntity) o;

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
