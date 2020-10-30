package uk.gov.companieshouse.model.email;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import uk.gov.companieshouse.email_producer.model.EmailData;
import uk.gov.companieshouse.model.DocumentDetails;

public class ApplicationConfirmationEmailData extends EmailData {

    private String suppressionReference;
    private DocumentDetails documentDetails;
    private String documentDate;
    private String paymentReceived;
    private String processingDelayEvent;

    public ApplicationConfirmationEmailData() {
        this(null, null, null, null, null);
    }

    public ApplicationConfirmationEmailData(String suppressionReference, DocumentDetails documentDetails, String documentDate, String paymentReceived, String processingDelayEvent) {
    
        this.suppressionReference = suppressionReference;
        this.documentDetails = documentDetails;
        this.documentDate = documentDate;
        this.paymentReceived = paymentReceived;
        this.processingDelayEvent = processingDelayEvent;
    }

    public String getSuppressionReference() { return suppressionReference; }

    public DocumentDetails getDocumentDetails() { return documentDetails; }

    public String getDocumentDate() { return documentDate; }

    public String getPaymentReceived() { return paymentReceived; }

    public String getProcessingDelayEvent() { return processingDelayEvent; }

    public void setSuppressionReference(String suppressionReference) { this.suppressionReference = suppressionReference; }

    public void setDocumentDetails(DocumentDetails documentDetails) { this.documentDetails = documentDetails; }

    public void setDocumentDate(String documentDate) { this.documentDate = documentDate; }

    public void setPaymentReceived(String paymentReceived) { this.paymentReceived = paymentReceived; }

    public void setProcessingDelayEvent(String processingDelayEvent) { this.processingDelayEvent = processingDelayEvent; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApplicationConfirmationEmailData that = (ApplicationConfirmationEmailData) o;

        return new EqualsBuilder()
            .append(this.getTo(), that.getTo())
            .append(this.getSubject(), that.getSubject())
            .append(suppressionReference, that.suppressionReference)
            .append(documentDetails, that.documentDetails)
            .append(documentDate, that.documentDate)
            .append(paymentReceived, that.paymentReceived)
            .append(processingDelayEvent, that.processingDelayEvent)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.getTo())
            .append(this.getSubject())
            .append(suppressionReference)
            .append(documentDetails)
            .append(documentDate)
            .append(paymentReceived)
            .append(processingDelayEvent)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("to", this.getTo())
            .append("subject", this.getSubject())
            .append("suppressionReference", suppressionReference)
            .append("documentDetails", documentDetails)
            .append("documentDate", documentDate)
            .append("paymentReceived", paymentReceived)
            .append("processingDelayEvent", processingDelayEvent)
            .toString();
    }

}
