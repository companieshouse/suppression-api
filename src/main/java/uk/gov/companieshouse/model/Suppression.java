package uk.gov.companieshouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class Suppression {

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private String createdBy;

    @Pattern(regexp = "([A-Z0-9]{5}-[A-Z0-9]{5})|^$", message = "applicationReference format is invalid")
    private String applicationReference;

    private ApplicantDetails applicantDetails;
    private Address addressToRemove;
    private Address serviceAddress;
    private DocumentDetails documentDetails;
    private Address contactAddress;
    private String etag;
    private PaymentDetails paymentDetails;

    public Suppression() {
        this(null, null, null, null, null, null, null, null, null, null);
    }

    public Suppression(LocalDateTime createdAt,
                       String createdBy,
                       String applicationReference,
                       ApplicantDetails applicantDetails,
                       Address addressToRemove,
                       Address serviceAddress,
                       DocumentDetails documentDetails,
                       Address contactAddress,
                       String etag,
                       PaymentDetails paymentDetails) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.applicationReference = applicationReference;
        this.applicantDetails = applicantDetails;
        this.addressToRemove = addressToRemove;
        this.serviceAddress = serviceAddress;
        this.documentDetails = documentDetails;
        this.contactAddress = contactAddress;
        this.etag = etag;
        this.paymentDetails = paymentDetails;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() { return createdBy; }

    public String getApplicationReference() { return applicationReference; }

    public ApplicantDetails getApplicantDetails() { return applicantDetails; }

    public Address getAddressToRemove() { return addressToRemove; }

    public Address getServiceAddress() { return serviceAddress; }

    public DocumentDetails getDocumentDetails() { return documentDetails; }

    public Address getContactAddress() { return contactAddress; }

    public String getEtag() { return etag; }

    public PaymentDetails getPaymentDetails() { return paymentDetails; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public void setApplicationReference(String applicationReference) { this.applicationReference = applicationReference; }

    public void setApplicantDetails(ApplicantDetails applicantDetails) { this.applicantDetails = applicantDetails; }

    public void setAddressToRemove(Address addressToRemove) { this.addressToRemove = addressToRemove; }

    public void setServiceAddress(Address serviceAddress) { this.serviceAddress = serviceAddress; }

    public void setDocumentDetails(DocumentDetails documentDetails) { this.documentDetails = documentDetails; }

    public void setContactAddress(Address contactAddress) { this.contactAddress = contactAddress; }

    public void setEtag(String etag) { this.etag = etag; }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Suppression that = (Suppression) o;

        return new EqualsBuilder()
            .append(createdAt, that.createdAt)
            .append(createdBy, that.createdBy)
            .append(applicationReference, that.applicationReference)
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
            .append(createdAt)
            .append(createdBy)
            .append(applicationReference)
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
            .append("createdAt", createdAt)
            .append("createdBy", createdBy)
            .append("applicationReference", applicationReference)
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
