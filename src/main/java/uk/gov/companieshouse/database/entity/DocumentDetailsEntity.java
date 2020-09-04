package uk.gov.companieshouse.database.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.AccessType;
import uk.gov.companieshouse.model.DocumentDetails;

import java.io.Serializable;

@AccessType(AccessType.Type.PROPERTY)
public class DocumentDetailsEntity implements Serializable {

    private final String companyName;
    private final String companyNumber;
    private final String description;
    private final String date;

    public DocumentDetailsEntity(String companyName, String companyNumber, String description, String date){
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.description = description;
        this.date = date;
    }

    public String getCompanyName() { return this.companyName; }

    public String getCompanyNumber() { return this.companyNumber; }

    public String getDescription() { return this.description; }

    public String getDate() { return this.date; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DocumentDetailsEntity that = (DocumentDetailsEntity) o;

        return new EqualsBuilder()
            .append(companyName, that.companyName)
            .append(companyNumber, that.companyNumber)
            .append(description, that.description)
            .append(date, that.date)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(companyName)
            .append(companyNumber)
            .append(description)
            .append(date)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("companyName", companyName)
            .append("companyNumber", companyNumber)
            .append("description", description)
            .append("date", date)
            .toString();
    }
}
