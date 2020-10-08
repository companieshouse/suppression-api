package uk.gov.companieshouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class SuppressionRequest {

    @JsonIgnore
    private LocalDateTime createdAt;

    @Pattern(regexp = "([A-Z0-9]{5}-[A-Z0-9]{5})|^$", message = "applicationReference format is invalid")
    private String applicationReference;

    @Valid
    @NotNull
    private ApplicantDetails applicantDetails;

    @JsonIgnore
    private String etag;

    public SuppressionRequest() {
        this(null, null, null, null);
    }

    public SuppressionRequest(LocalDateTime createdAt, String applicationReference, ApplicantDetails applicantDetails, String etag) {
        this.createdAt = createdAt;
        this.applicationReference = applicationReference;
        this.applicantDetails = applicantDetails;
        this.etag = etag;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getApplicationReference() {
        return applicationReference;
    }

    public void setApplicationReference(String applicationReference) {
        this.applicationReference = applicationReference;
    }

    public ApplicantDetails getApplicantDetails() {
        return applicantDetails;
    }

    public void setApplicantDetails(ApplicantDetails applicantDetails) {
        this.applicantDetails = applicantDetails;
    }

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

        SuppressionRequest that = (SuppressionRequest) o;

        return new EqualsBuilder()
            .append(createdAt, that.createdAt)
            .append(applicationReference, that.applicationReference)
            .append(applicantDetails, that.applicantDetails)
            .append(etag, that.etag)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(createdAt)
            .append(applicationReference)
            .append(applicantDetails)
            .append(etag)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("createdAt", createdAt)
            .append("applicationReference", applicationReference)
            .append("applicantDetails", applicantDetails)
            .append("etag", etag)
            .toString();
    }
}
