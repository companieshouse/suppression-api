package uk.gov.companieshouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import uk.gov.companieshouse.config.EmailConfig;
import uk.gov.companieshouse.config.PaymentConfig;
import uk.gov.companieshouse.model.email.ApplicationReceivedEmailData;
import uk.gov.companieshouse.model.email.ApplicationConfirmationEmailData;
import uk.gov.companieshouse.email_producer.model.EmailData;
import uk.gov.companieshouse.email_producer.EmailProducer;
import uk.gov.companieshouse.email_producer.EmailSendingException;
import uk.gov.companieshouse.model.DocumentDetails;
import uk.gov.companieshouse.model.Suppression;

import static uk.gov.companieshouse.model.Constants.APPLICATION_RECEIVED_MESSAGE_TYPE;
import static uk.gov.companieshouse.model.Constants.APPLICATION_RECEIVED_EMAIL_SUBJECT;
import static uk.gov.companieshouse.model.Constants.APPLICATION_CONFIRMATION_MESSAGE_TYPE;
import static uk.gov.companieshouse.model.Constants.APPLICATION_CONFIRMATION_EMAIL_SUBJECT;
import static uk.gov.companieshouse.utils.DateConverter.convertDateToHumanReadableFormat;


@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final EmailConfig emailConfig;
    private final EmailProducer emailProducer;
    private final PaymentConfig paymentConfig;

    public EmailService(EmailConfig emailConfig, EmailProducer emailProducer, PaymentConfig paymentConfig) {
        this.emailConfig = emailConfig;
        this.emailProducer = emailProducer;
        this.paymentConfig = paymentConfig;
    }

    public void sendToStaff(Suppression suppression) {
        String subject = String.format(APPLICATION_RECEIVED_EMAIL_SUBJECT, suppression.getApplicationReference());
        String applicantDateOfBirth = convertDateToHumanReadableFormat(suppression.getApplicantDetails().getDateOfBirth());
        String documentDate = convertDateToHumanReadableFormat(suppression.getDocumentDetails().getDate());
        
        final ApplicationReceivedEmailData emailData = new ApplicationReceivedEmailData();
        emailData.setTo(emailConfig.getChEmail());
        emailData.setSubject(subject);
        emailData.setSuppression(suppression);
        emailData.setApplicantDateOfBirth(applicantDateOfBirth);
        emailData.setDocumentDate(documentDate);

        LOGGER.debug("Sending Submission email for #{} to staff", emailData.getSuppression().getApplicationReference());
        this.sendEmail(emailData, APPLICATION_RECEIVED_MESSAGE_TYPE);
    }

    public void sendToUser(Suppression suppression) {
        String applicationReference = suppression.getApplicationReference();
        DocumentDetails documentDetails = suppression.getDocumentDetails();
        String subject = String.format(APPLICATION_CONFIRMATION_EMAIL_SUBJECT, applicationReference);
        String documentDate = convertDateToHumanReadableFormat(documentDetails.getDate());

        final ApplicationConfirmationEmailData emailData = new ApplicationConfirmationEmailData();
        emailData.setTo(suppression.getApplicantDetails().getEmailAddress());
        emailData.setSubject(subject);
        emailData.setSuppressionReference(applicationReference);
        emailData.setDocumentDetails(documentDetails);
        emailData.setDocumentDate(documentDate);
        emailData.setPaymentReceived(paymentConfig.getAmount());
        emailData.setProcessingDelayEvent(emailConfig.getProcessingDelayEvent());

        LOGGER.debug("Sending Confirmation email for #{} to user", applicationReference);
        this.sendEmail(emailData, APPLICATION_CONFIRMATION_MESSAGE_TYPE);
    }

    private void sendEmail(EmailData emailData, String messageType) throws EmailSendingException {
        try {
            emailProducer.sendEmail(emailData, messageType);
            LOGGER.info("Submitted {} email to Kafka", messageType);
        } catch (EmailSendingException err) {
            LOGGER.error("Error sending email", err);
            throw err;
        } 
    }
}
