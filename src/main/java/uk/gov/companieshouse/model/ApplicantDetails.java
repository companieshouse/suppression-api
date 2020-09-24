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

    @NotBlank(message = "date of birth must not be blank")
    private String dateOfBirth;

    public ApplicantDetails() {
        this(null, null, null, null);
    }

    public ApplicantDetails(String fullName, String previousName, String emailAddress, String dateOfBirth){
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.previousName = previousName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() { return this.fullName; }

    public String getPreviousName() { return this.previousName; }

    public String getEmailAddress() { return this.emailAddress; }

    public String getDateOfBirth() { return this.dateOfBirth; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setPreviousName(String previousName) { this.previousName = previousName; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplicantDetails that = (ApplicantDetails) o;

        return new EqualsBuilder()
            .append(fullName, that.fullName)
            .append(previousName, that.previousName)
            .append(emailAddress, that.emailAddress)
            .append(dateOfBirth, that.dateOfBirth)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(fullName)
            .append(previousName)
            .append(emailAddress)
            .append(dateOfBirth)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fullName", fullName)
            .append("previousName", previousName)
            .append("emailAddress", emailAddress)
            .append("dateOfBirth", dateOfBirth)
            .toString();
    }

}
