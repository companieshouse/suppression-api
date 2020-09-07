package uk.gov.companieshouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Suppression {

    @JsonIgnore
    private String id;

    @JsonIgnore
    private LocalDateTime createdAt;

    @Valid
    @NotNull(message = "applicant details must not be null")
    private ApplicantDetails applicantDetails;

    @Valid
    @NotNull(message = "addressToRemove must not be null")
    private Address addressToRemove;

    @Valid
    @NotNull(message = "document details must not be null")
    private DocumentDetails documentDetails;

    public Suppression() {
        this(null, null, null, null, null);
    }

    public Suppression(String id,
                       LocalDateTime createdAt,
                       ApplicantDetails applicantDetails,
                       Address addressToRemove,
                       DocumentDetails documentDetails) {

        this.id = id;
        this.createdAt = createdAt;
        this.applicantDetails = applicantDetails;
        this.addressToRemove = addressToRemove;
        this.documentDetails = documentDetails;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ApplicantDetails getApplicantDetails() { return this.applicantDetails; }

    public Address getAddressToRemove() { return this.addressToRemove; }

    public DocumentDetails getDocumentDetails() { return this.documentDetails; }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setApplicantDetails(ApplicantDetails applicantDetails) { this.applicantDetails = applicantDetails; }

    public void setAddressToRemove(Address addressToRemove) { this.addressToRemove = addressToRemove; }

    public void setDocumentDetails(DocumentDetails documentDetails) { this.documentDetails = documentDetails; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Suppression that = (Suppression) o;

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
