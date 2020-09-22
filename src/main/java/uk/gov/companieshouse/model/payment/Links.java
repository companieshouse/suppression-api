package uk.gov.companieshouse.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Links {
    
    private String self;

    @JsonProperty("suppression_request")
    private String payment;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Links that = (Links) o;
        return Objects.equals(self, that.self) &&
            Objects.equals(payment, that.payment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(self, payment);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("self", self)
            .append("payment", payment)
            .toString();
    }
}
