package uk.gov.companieshouse.database.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.AccessType;

import java.io.Serializable;

@AccessType(AccessType.Type.PROPERTY)
public class ApplicantDetailsEntity implements Serializable {

    private final String fullName;
    private final String previousName;
    private final String emailAddress;
    private final String dateOfBirth;

    public ApplicantDetailsEntity(String fullName, String previousName, String emailAddress, String dateOfBirth){
        this.fullName = fullName;
        this.previousName = previousName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() { return this.fullName; }

    public String getPreviousName() { return this.previousName; }

    public String getEmailAddress() { return this.emailAddress; }

    public String getDateOfBirth() { return this.dateOfBirth; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplicantDetailsEntity that = (ApplicantDetailsEntity) o;

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
