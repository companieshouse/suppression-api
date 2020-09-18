package uk.gov.companieshouse.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.TestData;
import uk.gov.companieshouse.database.entity.AddressEntity;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.model.Address;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.DocumentDetails;
import uk.gov.companieshouse.model.Suppression;
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
public class SuppressionServiceTest {

    @InjectMocks
    private SuppressionService suppressionService;

    @Mock
    private SuppressionMapper suppressionMapper;

    @Mock
    private SuppressionRepository suppressionRepository;

    @Test
    public void isExistingSuppressionID_returnsFalse() {

        String existingID = "reference#01";
        when(suppressionRepository.findById(existingID)).thenReturn(Optional.empty());
        assertFalse(suppressionService.isExistingSuppressionID(existingID));
    }

    @Test
    public void isExistingSuppressionID_returnsTrue() {

        String nonExistingID = "reference#01";
        when(suppressionRepository.findById(nonExistingID)).thenReturn(Optional.of(createSuppressionEntity(nonExistingID)));
        assertTrue(suppressionService.isExistingSuppressionID(nonExistingID));
    }

    @Test
    public void testSaveSuppression_returnsResourceReference() {

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(createSuppressionEntity("reference#01"));
        when(suppressionRepository.save(any(SuppressionEntity.class))).thenReturn(createSuppressionEntity(TestData.Suppression.applicationReference));

        assertEquals(TestData.Suppression.applicationReference, suppressionService.saveSuppression(createSuppression("reference#01")));
    }

    @Test
    public void testSaveSuppressionWithEmptyReference_returnsResourceReference() {

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(createSuppressionEntity("reference#01"));
        when(suppressionRepository.save(any(SuppressionEntity.class))).thenReturn(createSuppressionEntity(TestData.Suppression.applicationReference));

        String expectedReference = suppressionService.saveSuppression(createSuppression(""));

        verify(suppressionRepository, times(1)).findById(any(String.class));
        assertEquals(TestData.Suppression.applicationReference, expectedReference);
    }

    @Test
    public void generateUniqueSuppressionsReference_noDuplicatesPresent() {

        when(suppressionRepository.findById(any(String.class))).thenReturn(Optional.empty());

        suppressionService.generateUniqueSuppressionReference();

        verify(suppressionRepository, times(1)).findById(any(String.class));
    }

    @Test
    public void generateUniqueSuppressionsReference_duplicatesPresent() {

        SuppressionEntity suppressionEntity = createSuppressionEntity(TestData.Suppression.applicationReference);

        when(suppressionRepository.findById(any(String.class)))
            .thenReturn(Optional.of(suppressionEntity))
            .thenReturn(Optional.of(suppressionEntity))
            .thenReturn(Optional.of(suppressionEntity))
            .thenReturn(Optional.empty());

        suppressionService.generateUniqueSuppressionReference();

        verify(suppressionRepository, times(4)).findById(any(String.class));
    }

    private Suppression createSuppression(String reference) {
        return new Suppression(
            TestData.Suppression.createdAt,
            reference,
            new ApplicantDetails(
                TestData.Suppression.ApplicantDetails.fullName,
                TestData.Suppression.ApplicantDetails.previousName,
                TestData.Suppression.ApplicantDetails.fullName
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
                TestData.Suppression.ApplicantDetails.emailAddress
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
            TestData.Suppression.etag
        );
    }

}
