package uk.gov.companieshouse.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

public class DocumentDetails {

    @NotBlank(message = "companyName must not be blank")
    private String companyName;

    @NotBlank(message = "companyNumber must not be blank")
    private String companyNumber;

    @NotBlank(message = "description must not be blank")
    private String description;

    @NotBlank(message = "date must not be blank")
    private String date;

    public DocumentDetails() {
        this(null, null, null, null);
    }

    public DocumentDetails(String companyName, String companyNumber, String description, String date){
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.description = description;
        this.date = date;
    }

    public String getCompanyName() { return this.companyName; }

    public String getCompanyNumber() { return this.companyNumber; }

    public String getDescription() { return this.description; }

    public String getDate() { return this.date; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public void setCompanyNumber(String companyNumber) { this.companyNumber = companyNumber; }

    public void setDescription(String description) { this.description = description; }

    public void setDate(String date) { this.date = date; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DocumentDetails that = (DocumentDetails) o;

        return new EqualsBuilder()
            .append(companyName, that.companyName)
            .append(companyNumber, that.companyNumber)
            .append(description, that.description)
            .append(date, that.date)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
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
