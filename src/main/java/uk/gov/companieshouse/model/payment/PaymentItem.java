package uk.gov.companieshouse.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PaymentItem {
    
    private String description;
    
    @JsonProperty("description_identifier")
    private String descriptionIdentifier;
    
    @JsonProperty("description_values")
    private Map<String, String> descriptionValues;
    
    @JsonProperty("product_type")
    private String productType;
    
    private String amount;
    
    @JsonProperty("available_payment_methods")
    private List<String> availablePaymentMethods;
    
    @JsonProperty("class_of_payment")
    private List<String> classOfPayment;
    
    private String kind;
    
    @JsonProperty("resource_kind")
    private String resourceKind;

    public PaymentItem() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public PaymentItem(String description, String descriptionIdentifier, Map<String, String> descriptionValues,
                       String productType, String amount, List<String> availablePaymentMethods,
                       List<String> classOfPayment, String kind, String resourceKind) {
        this.description = description;
        this.descriptionIdentifier = descriptionIdentifier;
        this.descriptionValues = descriptionValues;
        this.productType = productType;
        this.amount = amount;
        this.availablePaymentMethods = availablePaymentMethods;
        this.classOfPayment = classOfPayment;
        this.kind = kind;
        this.resourceKind = resourceKind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionIdentifier() {
        return descriptionIdentifier;
    }

    public void setDescriptionIdentifier(String descriptionIdentifier) {
        this.descriptionIdentifier = descriptionIdentifier;
    }

    public Map<String, String> getDescriptionValues() {
        return descriptionValues;
    }

    public void setDescriptionValues(Map<String, String> descriptionValues) {
        this.descriptionValues = descriptionValues;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<String> getAvailablePaymentMethods() {
        return availablePaymentMethods;
    }

    public void setAvailablePaymentMethods(List<String> availablePaymentMethods) {
        this.availablePaymentMethods = availablePaymentMethods;
    }

    public List<String> getClassOfPayment() {
        return classOfPayment;
    }

    public void setClassOfPayment(List<String> classOfPayment) {
        this.classOfPayment = classOfPayment;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getResourceKind() {
        return resourceKind;
    }

    public void setResourceKind(String resourceKind) {
        this.resourceKind = resourceKind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentItem that = (PaymentItem) o;
        return Objects.equals(description, that.description) &&
            Objects.equals(descriptionIdentifier, that.descriptionIdentifier) &&
            Objects.equals(descriptionValues, that.descriptionValues) &&
            Objects.equals(productType, that.productType) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(availablePaymentMethods, that.availablePaymentMethods) &&
            Objects.equals(classOfPayment, that.classOfPayment) &&
            Objects.equals(kind, that.kind) &&
            Objects.equals(resourceKind, that.resourceKind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, descriptionIdentifier, descriptionValues, productType, amount, availablePaymentMethods, classOfPayment, kind, resourceKind);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("description", description)
            .append("descriptionIdentifier", descriptionIdentifier)
            .append("descriptionValues", descriptionValues)
            .append("productType", productType)
            .append("amount", amount)
            .append("availablePaymentMethods", availablePaymentMethods)
            .append("classOfPayment", classOfPayment)
            .append("kind", kind)
            .append("resourceKind", resourceKind)
            .toString();
    }
}
