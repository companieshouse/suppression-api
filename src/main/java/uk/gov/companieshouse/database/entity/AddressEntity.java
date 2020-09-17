package uk.gov.companieshouse.database.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.AccessType;

import java.io.Serializable;

@AccessType(AccessType.Type.PROPERTY)
public class AddressEntity implements Serializable {

    private final String line1;
    private final String line2;
    private final String town;
    private final String county;
    private final String postcode;
    private final String country;

    public AddressEntity(String line1, String line2, String town, String county, String postcode, String country){
        this.line1 = line1;
        this.line2 = line2;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
        this.country = country;
    }

    public String getLine1() { return this.line1; }

    public String getLine2() { return this.line2; }

    public String getTown() { return this.town; }

    public String getCounty() { return this.county; }

    public String getPostcode() { return this.postcode; }

    public String getCountry() { return this.country; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AddressEntity that = (AddressEntity) o;

        return new EqualsBuilder()
            .append(line1, that.line1)
            .append(line2, that.line2)
            .append(town, that.town)
            .append(county, that.county)
            .append(postcode, that.postcode)
            .append(country, that.country)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(line1)
            .append(line2)
            .append(town)
            .append(county)
            .append(postcode)
            .append(country)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("line1", line1)
            .append("line2", line2)
            .append("town", town)
            .append("county", county)
            .append("postcode", postcode)
            .append("country", country)
            .toString();
    }
}
