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
    private final DocumentDetailsEntity documentDetails;

    public SuppressionEntity(String id,
                             LocalDateTime createdAt,
                             ApplicantDetailsEntity applicantDetails,
                             AddressEntity addressToRemove,
                             DocumentDetailsEntity documentDetails) {

        this.id = id;
        this.createdAt = createdAt;
        this.applicantDetails = applicantDetails;
        this.addressToRemove = addressToRemove;
        this.documentDetails = documentDetails;
    }

    public String getId() { return this.id; }

    public LocalDateTime getCreatedAt() { return this.createdAt; }

    public ApplicantDetailsEntity getApplicantDetails() { return this.applicantDetails; }

    public AddressEntity getAddressToRemove() { return this.addressToRemove; }

    public DocumentDetailsEntity getDocumentDetails() { return this.documentDetails; }

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
            .append(documentDetails, that.documentDetails)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(createdAt)
            .append(applicantDetails)
            .append(addressToRemove)
            .append(documentDetails)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("createdAt", createdAt)
            .append("applicantDetails", applicantDetails)
            .append("addressToRemove", addressToRemove)
            .append("documentDetails", documentDetails)
            .toString();
    }
}
