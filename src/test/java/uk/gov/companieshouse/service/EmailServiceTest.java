package uk.gov.companieshouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.email_producer.EmailProducer;
import uk.gov.companieshouse.email_producer.EmailSendingException;
import uk.gov.companieshouse.config.EmailConfig;
import uk.gov.companieshouse.config.PaymentConfig;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.email.ApplicationConfirmationEmailData;
import uk.gov.companieshouse.model.email.ApplicationReceivedEmailData;
import uk.gov.companieshouse.fixtures.SuppressionFixtures;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    private static final String TEST_SUPPRESSION_ID = "reference#01";
    private static final String TEST_CH_EMAIL = "test@ch.gov.uk";
    
    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailConfig emailConfig;

    @Mock
    private EmailProducer emailKafkaProducer;

    @Mock
    private PaymentConfig paymentConfig;

    @Mock
    private Logger logger;

    @Test
    public void sendToStaff__ok() throws EmailSendingException {
        when(emailConfig.getChEmail()).thenReturn(TEST_CH_EMAIL);
        final ArgumentCaptor<ApplicationReceivedEmailData> dataCaptor = ArgumentCaptor.forClass(ApplicationReceivedEmailData.class);

        emailService.sendToStaff(SuppressionFixtures.generateSuppression(TEST_SUPPRESSION_ID));

        verify(emailKafkaProducer, times(1)).sendEmail(dataCaptor.capture(), eq("suppression_application_received"));
        ApplicationReceivedEmailData sentEmailData = dataCaptor.getValue();
        assertEquals(TEST_CH_EMAIL, sentEmailData.getTo());
        assertEquals("Application received: reference#01", sentEmailData.getSubject());
        assertEquals("reference#01", sentEmailData.getSuppression().getApplicationReference());
        assertEquals("1 May 1980", sentEmailData.getApplicantDateOfBirth());
        assertEquals("1 January 2000", sentEmailData.getDocumentDate());
    }

    @Test
    public void sendToStaff__err() throws EmailSendingException {
        Suppression testSuppression = SuppressionFixtures.generateSuppression(TEST_SUPPRESSION_ID);
        doThrow(EmailSendingException.class)
            .when(emailKafkaProducer)
            .sendEmail(any(), any());

        assertThrows(
            EmailSendingException.class,
            () -> emailService.sendToStaff(testSuppression)
        );
    }

    @Test
    public void sendToUser__ok() throws EmailSendingException {
        when(emailConfig.getProcessingDelayEvent()).thenReturn("");
        when(paymentConfig.getAmount()).thenReturn("32");

        final ArgumentCaptor<ApplicationConfirmationEmailData> dataCaptor = ArgumentCaptor.forClass(ApplicationConfirmationEmailData.class);

        emailService.sendToUser(SuppressionFixtures.generateSuppression(TEST_SUPPRESSION_ID));

        verify(emailKafkaProducer, times(1)).sendEmail(dataCaptor.capture(), eq("suppression_application_confirmation"));
        ApplicationConfirmationEmailData sentEmailData = dataCaptor.getValue();

        String expectedSubject = "Application to remove your home address from the Companies House register submitted: reference#01";
        assertEquals("user@example.com", sentEmailData.getTo());
        assertEquals(expectedSubject, sentEmailData.getSubject());
        assertEquals("reference#01", sentEmailData.getSuppressionReference());
        assertEquals("COMPANYNUMBER#1", sentEmailData.getDocumentDetails().getCompanyNumber());
        assertEquals("1 January 2000", sentEmailData.getDocumentDate());
        assertEquals("32", sentEmailData.getPaymentReceived());
        assertEquals("", sentEmailData.getProcessingDelayEvent());
    }

    @Test
    public void sendToUser__err() throws EmailSendingException {
        Suppression testSuppression = SuppressionFixtures.generateSuppression(TEST_SUPPRESSION_ID);
        doThrow(EmailSendingException.class)
            .when(emailKafkaProducer)
            .sendEmail(any(), any());

        assertThrows(
            EmailSendingException.class,
            () -> emailService.sendToUser(testSuppression)
        );
    }
}
