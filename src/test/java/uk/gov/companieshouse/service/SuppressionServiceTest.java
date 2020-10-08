package uk.gov.companieshouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.database.entity.AddressEntity;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.mapper.SuppressionRequestMapper;
import uk.gov.companieshouse.model.*;
import uk.gov.companieshouse.repository.SuppressionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SuppressionServiceTest {

    private static final String TEST_SUPPRESSION_ID = "reference#01";

    @InjectMocks
    private SuppressionService suppressionService;

    @Mock
    private SuppressionMapper suppressionMapper;

    @Mock
    private SuppressionRequestMapper suppressionRequestMapper;

    @Mock
    private SuppressionRepository suppressionRepository;

    private ArgumentCaptor<SuppressionEntity> suppressionArgumentCaptor;

    @BeforeEach
    void init() {
        suppressionArgumentCaptor = ArgumentCaptor.forClass(SuppressionEntity.class);
    }

    @Test
    void isExistingSuppressionID_returnsFalse() {

        when(suppressionRepository.findById(TEST_SUPPRESSION_ID)).thenReturn(Optional.empty());
        assertFalse(suppressionService.isExistingSuppressionID(TEST_SUPPRESSION_ID));
    }

    @Test
    void isExistingSuppressionID_returnsTrue() {

        when(suppressionRepository.findById(TEST_SUPPRESSION_ID)).thenReturn(Optional.of(createSuppressionEntity(TEST_SUPPRESSION_ID)));
        assertTrue(suppressionService.isExistingSuppressionID(TEST_SUPPRESSION_ID));
    }

    @Test
    void testGetExistingSuppression_returnsResource() {

        final Suppression suppression = createSuppression(TEST_SUPPRESSION_ID);

        when(suppressionMapper.map(any(SuppressionEntity.class))).thenReturn(suppression);
        when(suppressionRepository.findById(TEST_SUPPRESSION_ID)).thenReturn(Optional.of(createSuppressionEntity(TEST_SUPPRESSION_ID)));

        assertEquals(Optional.of(suppression), suppressionService.getSuppression(TEST_SUPPRESSION_ID));
    }

    @Test
    void testGetNonExistingSuppression_returnsEmptyResource() {

        when(suppressionRepository.findById(TEST_SUPPRESSION_ID)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), suppressionService.getSuppression(TEST_SUPPRESSION_ID));
    }


    @Test
    void testSaveSuppression_returnsResourceReference() {

        when(suppressionRequestMapper.map(any(SuppressionRequest.class))).thenReturn(createSuppressionEntity(TEST_SUPPRESSION_ID));
        when(suppressionRepository.save(any(SuppressionEntity.class))).thenReturn(createSuppressionEntity(TestData.Suppression.applicationReference));

        assertEquals(TestData.Suppression.applicationReference, suppressionService.saveSuppression(createSuppressionRequest(TEST_SUPPRESSION_ID)));
    }

    @Test
    void testSaveSuppressionWithEmptyReference_returnsResourceReference() {

        when(suppressionRequestMapper.map(any(SuppressionRequest.class))).thenReturn(createSuppressionEntity(TEST_SUPPRESSION_ID));
        when(suppressionRepository.save(any(SuppressionEntity.class))).thenReturn(createSuppressionEntity(TestData.Suppression.applicationReference));

        String expectedReference = suppressionService.saveSuppression(createSuppressionRequest(""));

        verify(suppressionRepository, times(1)).findById(any(String.class));
        assertEquals(TestData.Suppression.applicationReference, expectedReference);
    }

    @Test
    void generateUniqueSuppressionReference_noDuplicatesPresent() {

        when(suppressionRepository.findById(any(String.class))).thenReturn(Optional.empty());

        suppressionService.generateUniqueSuppressionReference();

        verify(suppressionRepository, times(1)).findById(any(String.class));
    }

    @Test
    void generateUniqueSuppressionReference_duplicatesPresent() {

        SuppressionEntity suppressionEntity = createSuppressionEntity(TestData.Suppression.applicationReference);

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

        final SuppressionEntity patchedSuppressionEntity = createSuppressionEntity(TEST_SUPPRESSION_ID);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = createSuppression(TEST_SUPPRESSION_ID);
        suppression.setApplicantDetails(null);

        final Suppression patchSuppressionRequest = new Suppression();
        patchSuppressionRequest.setApplicantDetails(new ApplicantDetails(TestData.Suppression.ApplicantDetails.fullName,
            TestData.Suppression.ApplicantDetails.previousName,
            TestData.Suppression.ApplicantDetails.emailAddress,
            TestData.Suppression.ApplicantDetails.dateOfBirth));

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testPatchSuppression_addressToRemove() {

        final SuppressionEntity patchedSuppressionEntity = createSuppressionEntity(TEST_SUPPRESSION_ID);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = createSuppression(TEST_SUPPRESSION_ID);
        suppression.setAddressToRemove(null);

        final Suppression patchSuppressionRequest = new Suppression();
        patchSuppressionRequest.setAddressToRemove(getAddress());

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testPatchSuppression_serviceAddress() {

        final SuppressionEntity patchedSuppressionEntity = createSuppressionEntity(TEST_SUPPRESSION_ID);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = createSuppression(TEST_SUPPRESSION_ID);
        suppression.setServiceAddress(null);

        final Suppression patchSuppressionRequest = new Suppression();
        patchSuppressionRequest.setServiceAddress(getAddress());

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testPatchSuppression_documentDetails() {

        final SuppressionEntity patchedSuppressionEntity = createSuppressionEntity(TEST_SUPPRESSION_ID);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = createSuppression(TEST_SUPPRESSION_ID);
        suppression.setDocumentDetails(null);

        final Suppression patchSuppressionRequest = new Suppression();
        patchSuppressionRequest.setDocumentDetails(new DocumentDetails(TestData.Suppression.DocumentDetails.companyName,
            TestData.Suppression.DocumentDetails.companyNumber,
            TestData.Suppression.DocumentDetails.description,
            TestData.Suppression.DocumentDetails.date));

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    @Test
    void testPatchSuppression_contactAddress() {

        final SuppressionEntity patchedSuppressionEntity = createSuppressionEntity(TEST_SUPPRESSION_ID);

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(patchedSuppressionEntity);

        final Suppression suppression = createSuppression(TEST_SUPPRESSION_ID);
        suppression.setContactAddress(null);

        final Suppression patchSuppressionRequest = new Suppression();
        patchSuppressionRequest.setContactAddress(getAddress());

        suppressionService.patchSuppressionResource(suppression, patchSuppressionRequest);

        verify(suppressionRepository).save(suppressionArgumentCaptor.capture());

        assertEquals(patchedSuppressionEntity, suppressionArgumentCaptor.getValue());
    }

    private Suppression createSuppression(String reference) {
        return new Suppression(
            TestData.Suppression.createdAt,
            reference,
            new ApplicantDetails(
                TestData.Suppression.ApplicantDetails.fullName,
                TestData.Suppression.ApplicantDetails.previousName,
                TestData.Suppression.ApplicantDetails.emailAddress,
                TestData.Suppression.ApplicantDetails.dateOfBirth
            ),
            new Address(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            new Address(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            new DocumentDetails(
                TestData.Suppression.DocumentDetails.companyName,
                TestData.Suppression.DocumentDetails.companyNumber,
                TestData.Suppression.DocumentDetails.description,
                TestData.Suppression.DocumentDetails.date
            ),
            new Address(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            TestData.Suppression.etag
        );
    }

    private SuppressionRequest createSuppressionRequest(String reference) {
        return new SuppressionRequest(
            TestData.Suppression.createdAt,
            reference,
            new ApplicantDetails(
                TestData.Suppression.ApplicantDetails.fullName,
                TestData.Suppression.ApplicantDetails.previousName,
                TestData.Suppression.ApplicantDetails.emailAddress,
                TestData.Suppression.ApplicantDetails.dateOfBirth
            ),
            TestData.Suppression.etag
        );
    }

    private SuppressionEntity createSuppressionEntity(String id) {
        return new SuppressionEntity(
            id,
            TestData.Suppression.createdAt,
            new ApplicantDetailsEntity(
                TestData.Suppression.ApplicantDetails.fullName,
                TestData.Suppression.ApplicantDetails.previousName,
                TestData.Suppression.ApplicantDetails.emailAddress,
                TestData.Suppression.ApplicantDetails.dateOfBirth
            ),
            new AddressEntity(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            new AddressEntity(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            new DocumentDetailsEntity(
                TestData.Suppression.DocumentDetails.companyName,
                TestData.Suppression.DocumentDetails.companyNumber,
                TestData.Suppression.DocumentDetails.description,
                TestData.Suppression.DocumentDetails.date
            ),
            new AddressEntity(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode,
                TestData.Suppression.Address.country
            ),
            TestData.Suppression.etag
        );
    }

    private Address getAddress() {
        return new Address(TestData.Suppression.Address.line1,
            TestData.Suppression.Address.line2,
            TestData.Suppression.Address.town,
            TestData.Suppression.Address.county,
            TestData.Suppression.Address.country,
            TestData.Suppression.Address.postcode);
    }

}
