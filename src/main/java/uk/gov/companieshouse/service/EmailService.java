package uk.gov.companieshouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.companieshouse.config.EmailConfig;
import uk.gov.companieshouse.config.PaymentConfig;
import uk.gov.companieshouse.logging.Logger;
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

    private final EmailConfig emailConfig;
    private final EmailProducer emailProducer;
    private final PaymentConfig paymentConfig;
    private final Logger logger;

    @Autowired
    public EmailService(EmailConfig emailConfig, EmailProducer emailProducer, PaymentConfig paymentConfig, Logger logger) {
        this.emailConfig = emailConfig;
        this.emailProducer = emailProducer;
        this.paymentConfig = paymentConfig;
        this.logger = logger;
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

        logger.debug(String.format("Sending Submission email for #%s to staff", emailData.getSuppression().getApplicationReference()));
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

        logger.debug(String.format("Sending Confirmation email for #%s to user", applicationReference));
        this.sendEmail(emailData, APPLICATION_CONFIRMATION_MESSAGE_TYPE);
    }

    private void sendEmail(EmailData emailData, String messageType) throws EmailSendingException {
        try {
            emailProducer.sendEmail(emailData, messageType);
            logger.info(String.format("Submitted %s email to Kafka", messageType));
        } catch (EmailSendingException err) {
            logger.error("Error sending email", err);
            throw err;
        } 
    }
}
