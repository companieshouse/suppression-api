package uk.gov.companieshouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class Suppression {

    @JsonIgnore
    private LocalDateTime createdAt;

    @Pattern(regexp = "([A-Z0-9]{5}-[A-Z0-9]{5})|^$", message = "applicationReference format is invalid")
    private String applicationReference;

    @Valid
    @NotNull(message = "applicant details must not be null")
    private ApplicantDetails applicantDetails;

    @Valid
    @NotNull(message = "addressToRemove must not be null")
    private Address addressToRemove;

    @Valid
    @NotNull(message = "document details must not be null")
    private DocumentDetails documentDetails;

    @JsonProperty("ETag")
    private String etag;

    public Suppression() {
        this(null, null, null, null, null, null);
    }

    public Suppression(LocalDateTime createdAt,
                       String applicationReference,
                       ApplicantDetails applicantDetails,
                       Address addressToRemove,
                       DocumentDetails documentDetails,
                       String etag) {

        this.createdAt = createdAt;
        this.applicationReference = applicationReference;
        this.applicantDetails = applicantDetails;
        this.addressToRemove = addressToRemove;
        this.documentDetails = documentDetails;
        this.etag = etag;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public String getApplicationReference() { return this.applicationReference; }

    public ApplicantDetails getApplicantDetails() { return this.applicantDetails; }

    public Address getAddressToRemove() { return this.addressToRemove; }

    public DocumentDetails getDocumentDetails() { return this.documentDetails; }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setApplicationReference(String applicationReference) { this.applicationReference = applicationReference; }

    public void setApplicantDetails(ApplicantDetails applicantDetails) { this.applicantDetails = applicantDetails; }

    public void setAddressToRemove(Address addressToRemove) { this.addressToRemove = addressToRemove; }

    public void setDocumentDetails(DocumentDetails documentDetails) { this.documentDetails = documentDetails; }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Suppression that = (Suppression) o;

        return new EqualsBuilder()
            .append(createdAt, that.createdAt)
            .append(applicationReference, that.applicationReference)
            .append(applicantDetails, that.applicantDetails)
            .append(addressToRemove, that.addressToRemove)
            .append(documentDetails, that.documentDetails)
            .append(etag, that.etag)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(createdAt)
            .append(applicationReference)
            .append(applicantDetails)
            .append(addressToRemove)
            .append(documentDetails)
            .append(etag)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("createdAt", createdAt)
            .append("applicationReference", applicationReference)
            .append("applicantDetails", applicantDetails)
            .append("addressToRemove", addressToRemove)
            .append("documentDetails", documentDetails)
            .append("etag", etag)
            .toString();
    }
}
