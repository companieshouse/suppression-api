package uk.gov.companieshouse.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.gov.companieshouse.database.entity.AddressEntity;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ApplicantDetails {

    @NotBlank(message = "full name must not be blank")
    private String fullName;

    @NotBlank(message = "email address must not be blank")
    private String emailAddress;

    public ApplicantDetails() {
        this(null, null);
    }

    public ApplicantDetails(String fullName, String emailAddress){
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    public String getFullName() { return this.fullName; }

    public String getEmailAddress() { return this.emailAddress; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplicantDetails that = (ApplicantDetails) o;

        return new EqualsBuilder()
            .append(fullName, that.fullName)
            .append(emailAddress, that.emailAddress)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(fullName)
            .append(emailAddress)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fullName", fullName)
            .append("emailAddress", emailAddress)
            .toString();
    }




}
