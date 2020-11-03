package uk.gov.companieshouse.database.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "suppression")
@AccessType(AccessType.Type.PROPERTY)
public class SuppressionEntity implements Serializable {

    @Id
    @SuppressWarnings("FieldMayBeFinal") // Non final field is required by Spring Data
    private final String id;
    private final LocalDateTime createdAt;
    private final ApplicantDetailsEntity applicantDetails;
    private final AddressEntity addressToRemove;
    private final AddressEntity serviceAddress;
    private final DocumentDetailsEntity [] documentDetails;
    private final AddressEntity contactAddress;
    private final String etag;
    private final PaymentDetailsEntity paymentDetails;

    public SuppressionEntity(String id,
                             LocalDateTime createdAt,
                             ApplicantDetailsEntity applicantDetails,
                             AddressEntity addressToRemove,
                             AddressEntity serviceAddress,
                             DocumentDetailsEntity [] documentDetails,
                             AddressEntity contactAddress,
                             String etag,
                             PaymentDetailsEntity paymentDetails) {
        this.id = id;
        this.createdAt = createdAt;
        this.applicantDetails = applicantDetails;
        this.addressToRemove = addressToRemove;
        this.serviceAddress = serviceAddress;
        this.documentDetails = documentDetails;
        this.contactAddress = contactAddress;
        this.etag = etag;
        this.paymentDetails = paymentDetails;
    }

    public String getId() { return this.id; }

    public LocalDateTime getCreatedAt() { return this.createdAt; }

    public ApplicantDetailsEntity getApplicantDetails() { return this.applicantDetails; }

    public AddressEntity getAddressToRemove() { return this.addressToRemove; }

    public AddressEntity getServiceAddress() {
        return this.serviceAddress;
    }

    public DocumentDetailsEntity [] getDocumentDetails() { return this.documentDetails; }

    public AddressEntity getContactAddress() { return this.contactAddress; }

    public String getEtag() {
        return this.etag;
    }

    public PaymentDetailsEntity getPaymentDetails() {
        return this.paymentDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SuppressionEntity that = (SuppressionEntity) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(createdAt, that.createdAt)
            .append(applicantDetails, that.applicantDetails)
            .append(addressToRemove, that.addressToRemove)
            .append(serviceAddress, that.serviceAddress)
            .append(documentDetails, that.documentDetails)
            .append(contactAddress, that.contactAddress)
            .append(etag, that.etag)
            .append(paymentDetails, that.paymentDetails)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .append(createdAt)
            .append(applicantDetails)
            .append(addressToRemove)
            .append(serviceAddress)
            .append(documentDetails)
            .append(contactAddress)
            .append(etag)
            .append(paymentDetails)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("createdAt", createdAt)
            .append("applicantDetails", applicantDetails)
            .append("addressToRemove", addressToRemove)
            .append("serviceAddress", serviceAddress)
            .append("documentDetails", documentDetails)
            .append("contactAddress", contactAddress)
            .append("etag", etag)
            .append("paymentDetails", paymentDetails)
            .toString();
    }
}
