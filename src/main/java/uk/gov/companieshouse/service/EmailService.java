package uk.gov.companieshouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import uk.gov.companieshouse.config.EmailConfig;
import uk.gov.companieshouse.model.email.ApplicationReceivedEmailData;
import uk.gov.companieshouse.email_producer.model.EmailData;
import uk.gov.companieshouse.email_producer.EmailProducer;
import uk.gov.companieshouse.email_producer.EmailSendingException;
import uk.gov.companieshouse.model.Suppression;

import static uk.gov.companieshouse.model.Constants.APPLICATION_RECEIVED_MESSAGE_TYPE;
import static uk.gov.companieshouse.model.Constants.APPLICATION_RECEIVED_EMAIL_SUBJECT;
import static uk.gov.companieshouse.utils.DateConverter.convertDateToHumanReadableFormat;


@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final EmailConfig environmentConfig;
    private final EmailProducer emailProducer;

    public EmailService(EmailConfig environmentConfig, EmailProducer emailProducer) {
        this.environmentConfig = environmentConfig;
        this.emailProducer = emailProducer;
    }

    public void sendToStaff(Suppression suppression) {
        String subject = String.format(APPLICATION_RECEIVED_EMAIL_SUBJECT, suppression.getApplicationReference());
        String applicantDateOfBirth = convertDateToHumanReadableFormat(suppression.getApplicantDetails().getDateOfBirth());
        String documentDate = convertDateToHumanReadableFormat(suppression.getDocumentDetails().getDate());
        
        final ApplicationReceivedEmailData emailData = new ApplicationReceivedEmailData();
        emailData.setTo(environmentConfig.getChEmail());
        emailData.setSubject(subject);
        emailData.setSuppression(suppression);
        emailData.setApplicantDateOfBirth(applicantDateOfBirth);
        emailData.setDocumentDate(documentDate);

        LOGGER.debug("Sending Submission email for #{}", emailData.getSuppression().getApplicationReference());
        this.sendEmail(emailData, APPLICATION_RECEIVED_MESSAGE_TYPE);
    }

    private void sendEmail(EmailData emailData, String messageType) throws EmailSendingException {
        try {
            emailProducer.sendEmail(emailData, messageType);
            LOGGER.info("Submitted email to Kafka");
        } catch (EmailSendingException err) {
            LOGGER.error("Error sending email", err);
            throw err;
        } 
    }
}
