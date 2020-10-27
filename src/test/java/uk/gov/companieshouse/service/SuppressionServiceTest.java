package uk.gov.companieshouse.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.email_producer.EmailSendingException;
import uk.gov.companieshouse.fixtures.SuppressionFixtures;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.SuppressionPatchRequest;
import uk.gov.companieshouse.model.payment.PaymentPatchRequest;
import uk.gov.companieshouse.model.payment.PaymentStatus;
import uk.gov.companieshouse.repository.SuppressionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import static uk.gov.companieshouse.TestData.Suppression.applicationReference;
import static uk.gov.companieshouse.fixtures.SuppressionEntityFixtures.generateSuppressionEntity;
import static uk.gov.companieshouse.fixtures.SuppressionFixtures.generateAddress;
import static uk.gov.companieshouse.fixtures.SuppressionFixtures.generateSuppression;
import static uk.gov.companieshouse.fixtures.PaymentFixtures.generatePaymentPatchRequest;

@ExtendWith(MockitoExtension.class)
class SuppressionServiceTest {

    @InjectMocks
    private SuppressionService suppressionService;

    @Mock
    private SuppressionMapper suppressionMapper;

    @Mock
    private SuppressionRepository suppressionRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private Logger logger;

    private ArgumentCaptor<SuppressionEntity> suppressionArgumentCaptor;

    @BeforeEach
    void init() {
        suppressionArgumentCaptor = ArgumentCaptor.forClass(SuppressionEntity.class);
    }

    @Test
    void isExistingSuppressionID_returnsFalse() {

        when(suppressionRepository.findById(applicationReference)).thenReturn(Optional.empty());
        assertFalse(suppressionService.isExistingSuppressionID(applicationReference));
    }

    @Test
    void isExistingSuppressionID_returnsTrue() {

        when(suppressionRepository.findById(applicationReference)).thenReturn(Optional.of(generateSuppressionEntity(applicationReference)));
        assertTrue(suppressionService.isExistingSuppressionID(applicationReference));
    }

    @Test
    void testGetExistingSuppression_returnsResource() {

        final Suppression suppression = generateSuppression(applicationReference);

        when(suppressionMapper.map(any(SuppressionEntity.class))).thenReturn(suppression);
        when(suppressionRepository.findById(applicationReference)).thenReturn(Optional.of(generateSuppressionEntity(applicationReference)));

        assertEquals(Optional.of(suppression), suppressionService.getSuppression(applicationReference));
    }

    @Test
    void testGetNonExistingSuppression_returnsEmptyResource() {

        when(suppressionRepository.findById(applicationReference)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), suppressionService.getSuppression(applicationReference));
    }


    @Test
    void testSaveSuppression_returnsResourceReference() {

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(generateSuppressionEntity(applicationReference));
        when(suppressionRepository.save(any(SuppressionEntity.class))).thenReturn(generateSuppressionEntity(applicationReference));

        assertEquals(applicationReference, suppressionService.saveSuppression(SuppressionFixtures.generateApplicantDetails()));
    }

    @Test
    void testSaveSuppressionWithEmptyReference_returnsResourceReference() {

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(generateSuppressionEntity(applicationReference));
        when(suppressionRepository.save(any(SuppressionEntity.class))).thenReturn(generateSuppressionEntity(applicationReference));

        String expectedReference = suppressionService.saveSuppression(SuppressionFixtures.generateApplicantDetails());

        verify(suppressionRepository, times(1)).findById(any(String.class));
        assertEquals(applicationReference, expectedReference);
    }

    @Test
    void testHandlePayment_savesPaymentDetails() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);
        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setPaymentDetails(null);

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);

        suppressionService.handlePayment(paymentDetails, suppression);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testHandlePayment__sendEmailWhenPaid() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);
        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);
        Suppression suppression = generateSuppression(applicationReference);

        suppressionService.handlePayment(paymentDetails, suppression);

        verify(emailService, times(1)).sendToStaff(suppression);
        verify(emailService, times(1)).sendToUser(suppression);
    }

    @Test
    void testHandlePayment__noEmailWhenCancelledPayment() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);
        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);


        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.CANCELLED);
        Suppression suppression = generateSuppression(applicationReference);

        suppressionService.handlePayment(paymentDetails, suppression);

        verify(emailService, times(0)).sendToStaff(any());
    }

    @Test
    void testHandlePayment__noEmailWhenFailedPayment() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);
        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);


        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.FAILED);
        Suppression suppression = generateSuppression(applicationReference);

        suppressionService.handlePayment(paymentDetails, suppression);

        verify(emailService, times(0)).sendToStaff(any());
    }

    @Test
    void testHandlePayment__errSendingEmail() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);
        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        PaymentPatchRequest paymentDetails = generatePaymentPatchRequest(PaymentStatus.PAID);
        Suppression suppression = generateSuppression(applicationReference);
        doThrow(EmailSendingException.class)
            .when(emailService)
            .sendToStaff(any());

        assertThrows(
            EmailSendingException.class,
            () -> suppressionService.handlePayment(paymentDetails, suppression)
        );

        verify(emailService, times(0)).sendToUser(suppression);
    }

    @Test
    void generateUniqueSuppressionReference_noDuplicatesPresent() {

        when(suppressionRepository.findById(any(String.class))).thenReturn(Optional.empty());

        suppressionService.generateUniqueSuppressionReference();

        verify(suppressionRepository, times(1)).findById(any(String.class));
    }

    @Test
    void generateUniqueSuppressionReference_duplicatesPresent() {

        SuppressionEntity suppressionEntity = generateSuppressionEntity(applicationReference);

        when(suppressionRepository.findById(any(String.class)))
            .thenReturn(Optional.of(suppressionEntity))
            .thenReturn(Optional.of(suppressionEntity))
            .thenReturn(Optional.of(suppressionEntity))
            .thenReturn(Optional.empty());

        suppressionService.generateUniqueSuppressionReference();

        verify(suppressionRepository, times(4)).findById(any(String.class));
    }

    @Test
    void testPatchSuppression_applicantDetails() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setApplicantDetails(null);

        final SuppressionPatchRequest patchSuppressionRequest = new SuppressionPatchRequest();
        patchSuppressionRequest.setApplicantDetails(SuppressionFixtures.generateApplicantDetails());

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testPatchSuppression_addressToRemove() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setAddressToRemove(null);

        final SuppressionPatchRequest patchSuppressionRequest = new SuppressionPatchRequest();
        patchSuppressionRequest.setAddressToRemove(generateAddress());

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testPatchSuppression_serviceAddress() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setServiceAddress(null);

        final SuppressionPatchRequest patchSuppressionRequest = new SuppressionPatchRequest();
        patchSuppressionRequest.setServiceAddress(generateAddress());

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testPatchSuppression_documentDetails() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setDocumentDetails(null);

        final SuppressionPatchRequest patchSuppressionRequest = new SuppressionPatchRequest();
        patchSuppressionRequest.setDocumentDetails(SuppressionFixtures.generateDocumentDetails());

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testPatchSuppression_contactAddress() {

        final SuppressionEntity patchedSuppressionEntity = generateSuppressionEntity(applicationReference);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = SuppressionFixtures.generateSuppression(applicationReference);
        suppression.setContactAddress(null);

        final SuppressionPatchRequest patchSuppressionRequest = new SuppressionPatchRequest();
        patchSuppressionRequest.setContactAddress(SuppressionFixtures.generateAddress());

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

}
