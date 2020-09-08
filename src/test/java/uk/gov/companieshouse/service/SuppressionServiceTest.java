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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuppressionServiceTest {

    @InjectMocks
    private SuppressionService suppressionService;

    @Mock
    private SuppressionMapper suppressionMapper;

    @Mock
    private SuppressionRepository suppressionRepository;

    @Test
    public void testCreateSuppression_returnsResourceId() {

        when(suppressionMapper.map(any(Suppression.class))).thenReturn(createSuppressionEntity("reference#01"));
        when(suppressionRepository.insert(any(SuppressionEntity.class))).thenReturn(createSuppressionEntity(TestData.Suppression.applicationReference));

        assertEquals(TestData.Suppression.applicationReference, suppressionService.saveSuppression(createSuppression("reference#01")));
    }

    private Suppression createSuppression(String applicationReference) {
        return new Suppression(
            TestData.Suppression.createdAt,
            applicationReference,
            new ApplicantDetails(
                TestData.Suppression.ApplicantDetails.fullName,
                TestData.Suppression.ApplicantDetails.fullName
            ),
            new Address(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode
            ),
            new DocumentDetails(
                TestData.Suppression.DocumentDetails.companyName,
                TestData.Suppression.DocumentDetails.companyNumber,
                TestData.Suppression.DocumentDetails.description,
                TestData.Suppression.DocumentDetails.date
            )
        );
    }

    private SuppressionEntity createSuppressionEntity(String id) {
        return new SuppressionEntity(
            id,
            TestData.Suppression.createdAt,
            new ApplicantDetailsEntity(
                TestData.Suppression.ApplicantDetails.fullName,
                TestData.Suppression.ApplicantDetails.emailAddress
            ),
            new AddressEntity(
                TestData.Suppression.Address.line1,
                TestData.Suppression.Address.line2,
                TestData.Suppression.Address.town,
                TestData.Suppression.Address.county,
                TestData.Suppression.Address.postcode
            ),
            new DocumentDetailsEntity(
                TestData.Suppression.DocumentDetails.companyName,
                TestData.Suppression.DocumentDetails.companyNumber,
                TestData.Suppression.DocumentDetails.description,
                TestData.Suppression.DocumentDetails.date
            )
        );
    }

}
