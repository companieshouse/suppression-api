package uk.gov.companieshouse.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

public class ApplicantDetails {

    @NotBlank(message = "full name must not be blank")
    private String fullName;

    private String previousName;

    @NotBlank(message = "email address must not be blank")
    private String emailAddress;

    public ApplicantDetails() {
        this(null, null, null);
    }

    public ApplicantDetails(String fullName, String previousName, String emailAddress){
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.previousName = previousName;
    }

    public String getFullName() { return this.fullName; }

    public String getPreviousName() { return this.previousName; }

    public String getEmailAddress() { return this.emailAddress; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setPreviousName(String previousName) { this.previousName = previousName; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplicantDetails that = (ApplicantDetails) o;

        return new EqualsBuilder()
            .append(fullName, that.fullName)
            .append(previousName, that.previousName)
            .append(emailAddress, that.emailAddress)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(fullName)
            .append(previousName)
            .append(emailAddress)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fullName", fullName)
            .append("previousName", previousName)
            .append("emailAddress", emailAddress)
            .toString();
    }

}
