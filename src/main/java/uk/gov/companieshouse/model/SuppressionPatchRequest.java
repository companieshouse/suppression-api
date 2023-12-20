package uk.gov.companieshouse.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.Valid;

public class SuppressionPatchRequest {

    @Valid
    private ApplicantDetails applicantDetails;

    @Valid
    private Address addressToRemove;

    private Address serviceAddress;

    @Valid
    private DocumentDetails documentDetails;

    @Valid
    private Address contactAddress;

    public SuppressionPatchRequest() {
        this(null, null, null, null, null);
    }

    public SuppressionPatchRequest(ApplicantDetails applicantDetails, Address addressToRemove, Address serviceAddress,
                                   DocumentDetails documentDetails, Address contactAddress) {
        this.applicantDetails = applicantDetails;
        this.addressToRemove = addressToRemove;
        this.serviceAddress = serviceAddress;
        this.documentDetails = documentDetails;
        this.contactAddress = contactAddress;
    }

    public ApplicantDetails getApplicantDetails() {
        return applicantDetails;
    }

    public void setApplicantDetails(ApplicantDetails applicantDetails) {
        this.applicantDetails = applicantDetails;
    }

    public Address getAddressToRemove() {
        return addressToRemove;
    }

    public void setAddressToRemove(Address addressToRemove) {
        this.addressToRemove = addressToRemove;
    }

    public Address getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(Address serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public DocumentDetails getDocumentDetails() {
        return documentDetails;
    }

    public void setDocumentDetails(DocumentDetails documentDetails) {
        this.documentDetails = documentDetails;
    }

    public Address getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(Address contactAddress) {
        this.contactAddress = contactAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SuppressionPatchRequest that = (SuppressionPatchRequest) o;

        return new EqualsBuilder()
            .append(applicantDetails, that.applicantDetails)
            .append(addressToRemove, that.addressToRemove)
            .append(serviceAddress, that.serviceAddress)
            .append(documentDetails, that.documentDetails)
            .append(contactAddress, that.contactAddress)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(applicantDetails)
            .append(addressToRemove)
            .append(serviceAddress)
            .append(documentDetails)
            .append(contactAddress)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("applicantDetails", applicantDetails)
            .append("addressToRemove", addressToRemove)
            .append("serviceAddress", serviceAddress)
            .append("documentDetails", documentDetails)
            .append("contactAddress", contactAddress)
            .toString();
    }
}
