package uk.gov.companieshouse.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

public class Payment {

    private String etag;

    private String kind;

    private Links links;

    @JsonProperty("company_number")
    private String companyNumber;

    private List<PaymentItem> items;

    public Payment() {}

    public Payment(String etag, String kind, Links links, String companyNumber, List<PaymentItem> items) {
        this.etag = etag;
        this.kind = kind;
        this.links = links;
        this.companyNumber = companyNumber;
        this.items = items;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public List<PaymentItem> getItems() {
        return items;
    }

    public void setItems(List<PaymentItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(etag, payment.etag) &&
            Objects.equals(kind, payment.kind) &&
            Objects.equals(links, payment.links) &&
            Objects.equals(companyNumber, payment.companyNumber) &&
            Objects.equals(items, payment.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etag, kind, links, companyNumber, items);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("etag", etag)
            .append("kind", kind)
            .append("links", links)
            .append("companyNumber", companyNumber)
            .append("items", items)
            .toString();
    }
}
