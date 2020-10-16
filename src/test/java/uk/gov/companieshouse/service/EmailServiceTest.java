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
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.email.ApplicationReceivedEmailData;
import uk.gov.companieshouse.fixtures.SuppressionFixtures;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    private static final String TEST_SUPPRESSION_ID = "reference#01";
    private static final String TEST_CH_EMAIL = "test@ch.gov.uk";
    private static final String TEST_CDN_HOST = "cdn.test";
    
    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailConfig environmentConfig;

    @Mock
    private EmailProducer emailKafkaProducer;

    @Test
    public void sendToStaff__ok() throws EmailSendingException {
        when(environmentConfig.getChEmail()).thenReturn(TEST_CH_EMAIL);
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
}
