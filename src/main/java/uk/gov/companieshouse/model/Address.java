package uk.gov.companieshouse.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

public class Address {

    @NotBlank(message = "line1 must not be blank")
    private String line1;

    private String line2;

    @NotBlank(message = "town must not be blank")
    private String town;

    @NotBlank(message = "county must not be blank")
    private String county;

    @NotBlank(message = "postcode must not be blank")
    private String postcode;

    public Address() {
        this(null, null, null, null, null);
    }

    public Address(String line1, String line2, String town, String county, String postcode){
        this.line1 = line1;
        this.line2 = line2;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
    }

    public String getLine1() { return this.line1; }

    public String getLine2() { return this.line2; }

    public String getTown() { return this.town; }

    public String getCounty() { return this.county; }

    public String getPostcode() { return this.postcode; }

    public void setLine1(String line1) { this.line1 = line1; }

    public void setLine2(String line2) { this.line2 = line2; }

    public void setTown(String town) { this.town = town; }

    public void setCounty(String county) { this.county = county; }

    public void setPostcode(String postcode) { this.postcode = postcode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Address that = (Address) o;

        return new EqualsBuilder()
            .append(line1, that.line1)
            .append(line2, that.line2)
            .append(town, that.town)
            .append(county, that.county)
            .append(postcode, that.postcode)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(line1)
            .append(line2)
            .append(town)
            .append(county)
            .append(postcode)
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
            .toString();
    }

}
