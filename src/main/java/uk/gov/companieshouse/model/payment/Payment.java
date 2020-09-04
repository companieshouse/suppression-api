package uk.gov.companieshouse.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Payment {
    
    @JsonProperty("Etag")
    private String etag;

    private String kind;
    private Links links;
    private List<Item> items;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
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
            Objects.equals(items, payment.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etag, kind, links, items);
    }

    @Override
    public String toString() {
        return "Payment{" +
            "etag='" + etag + '\'' +
            ", kind='" + kind + '\'' +
            ", links=" + links +
            ", items=" + items +
            '}';
    }
}
