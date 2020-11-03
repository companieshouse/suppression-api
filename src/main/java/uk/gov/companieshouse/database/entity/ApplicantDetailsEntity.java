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
    private final String dateOfBirth;

    public ApplicantDetailsEntity(String fullName, String previousName, String dateOfBirth){
        this.fullName = fullName;
        this.previousName = previousName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() { return this.fullName; }

    public String getPreviousName() { return this.previousName; }

    public String getDateOfBirth() { return this.dateOfBirth; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplicantDetailsEntity that = (ApplicantDetailsEntity) o;

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
