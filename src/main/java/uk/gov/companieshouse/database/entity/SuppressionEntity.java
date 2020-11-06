package uk.gov.companieshouse.database.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "suppression")
@AccessType(AccessType.Type.PROPERTY)
public class SuppressionEntity implements Serializable {

    @Id
    @SuppressWarnings("FieldMayBeFinal") // Non final field is required by Spring Data
    private final String id;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final ApplicantDetailsEntity applicantDetails;
    private final AddressEntity addressToRemove;
    private final AddressEntity serviceAddress;
    private final List<DocumentDetailsEntity> documentDetails;
    private final AddressEntity contactAddress;
    private final String etag;
    private final PaymentDetailsEntity paymentDetails;

    public SuppressionEntity(String id,
                             LocalDateTime createdAt,
                             String createdBy,
                             ApplicantDetailsEntity applicantDetails,
                             AddressEntity addressToRemove,
                             AddressEntity serviceAddress,
                             List<DocumentDetailsEntity> documentDetails,
                             AddressEntity contactAddress,
                             String etag,
                             PaymentDetailsEntity paymentDetails) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
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

    public String getCreatedBy() { return this.createdBy; }

    public ApplicantDetailsEntity getApplicantDetails() { return this.applicantDetails; }

    public AddressEntity getAddressToRemove() { return this.addressToRemove; }

    public AddressEntity getServiceAddress() {
        return this.serviceAddress;
    }

    public List<DocumentDetailsEntity> getDocumentDetails() { return this.documentDetails; }

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
            .append(createdBy, that.createdBy)
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
            .append(createdBy)
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
            .append("createdAt", createdBy)
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
