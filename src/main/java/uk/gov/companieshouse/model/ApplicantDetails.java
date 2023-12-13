package uk.gov.companieshouse.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.constraints.NotBlank;

public class ApplicantDetails {

    @NotBlank(message = "full name must not be blank")
    private String fullName;

    private String previousName;

    @NotBlank(message = "date of birth must not be blank")
    private String dateOfBirth;

    public ApplicantDetails() {
        this(null, null, null);
    }

    public ApplicantDetails(String fullName, String previousName, String dateOfBirth){
        this.fullName = fullName;
        this.previousName = previousName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() { return this.fullName; }

    public String getPreviousName() { return this.previousName; }

    public String getDateOfBirth() { return this.dateOfBirth; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setPreviousName(String previousName) { this.previousName = previousName; }

    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplicantDetails that = (ApplicantDetails) o;

        return new EqualsBuilder()
            .append(fullName, that.fullName)
            .append(previousName, that.previousName)
            .append(dateOfBirth, that.dateOfBirth)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(fullName)
            .append(previousName)
            .append(dateOfBirth)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fullName", fullName)
            .append("previousName", previousName)
            .append("dateOfBirth", dateOfBirth)
            .toString();
    }

}
