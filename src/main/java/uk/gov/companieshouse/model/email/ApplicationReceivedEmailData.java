package uk.gov.companieshouse.model.email;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import uk.gov.companieshouse.email_producer.model.EmailData;
import uk.gov.companieshouse.model.Suppression;

public class ApplicationReceivedEmailData extends EmailData {

    private Suppression suppression;
    private String applicantDateOfBirth;
    private String documentDate;
    private String contactEmailAddress;

    public ApplicationReceivedEmailData() {
        this(null, null, null, null);
    }

    public ApplicationReceivedEmailData(Suppression suppression, String applicantDateOfBirth, String documentDate, String contactEmailAddress) {
    
        this.suppression = suppression;
        this.applicantDateOfBirth = applicantDateOfBirth;
        this.documentDate = documentDate;
        this.contactEmailAddress = contactEmailAddress;
    }

    public Suppression getSuppression() { return suppression; }

    public String getApplicantDateOfBirth() { return applicantDateOfBirth; }

    public String getDocumentDate() { return documentDate; }

    public String getContactEmailAddress() { return contactEmailAddress; }

    public void setSuppression(Suppression suppression) { this.suppression = suppression; }

    public void setApplicantDateOfBirth(String applicantDateOfBirth) { this.applicantDateOfBirth = applicantDateOfBirth; }

    public void setDocumentDate(String documentDate) { this.documentDate = documentDate; }

    public void setContactEmailAddress(String contactEmailAddress) { this.contactEmailAddress = contactEmailAddress; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplicationReceivedEmailData that = (ApplicationReceivedEmailData) o;

        return new EqualsBuilder()
            .append(this.getTo(), that.getTo())
            .append(this.getSubject(), that.getSubject())
            .append(suppression, that.suppression)
            .append(applicantDateOfBirth, that.applicantDateOfBirth)
            .append(documentDate, that.documentDate)
            .append(contactEmailAddress, that.contactEmailAddress)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.getTo())
            .append(this.getSubject())
            .append(suppression)
            .append(applicantDateOfBirth)
            .append(documentDate)
            .append(contactEmailAddress)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("to", this.getTo())
            .append("subject", this.getSubject())
            .append("suppression", suppression)
            .append("applicantDateOfBirth", applicantDateOfBirth)
            .append("documentDate", documentDate)
            .append("contactEmailAddress", contactEmailAddress)
            .toString();
    }

}
