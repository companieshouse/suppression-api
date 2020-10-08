package uk.gov.companieshouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.GenerateEtagUtil;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.mapper.SuppressionRequestMapper;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.SuppressionPatchRequest;
import uk.gov.companieshouse.model.SuppressionRequest;
import uk.gov.companieshouse.repository.SuppressionRepository;
import uk.gov.companieshouse.utils.ReferenceGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SuppressionService {

    private final SuppressionMapper suppressionMapper;
    private final SuppressionRequestMapper suppressionRequestMapper;
    private final SuppressionRepository suppressionRepository;

    @Autowired
    public SuppressionService(SuppressionMapper suppressionMapper, SuppressionRequestMapper suppressionRequestMapper, SuppressionRepository suppressionRepository) {
        this.suppressionMapper = suppressionMapper;
        this.suppressionRequestMapper = suppressionRequestMapper;
        this.suppressionRepository = suppressionRepository;
    }

    public String saveSuppression(SuppressionRequest suppression) {

        suppression.setApplicationReference(generateUniqueSuppressionReference());
        suppression.setEtag(GenerateEtagUtil.generateEtag());
        suppression.setCreatedAt(LocalDateTime.now());

        return suppressionRepository.save(this.suppressionRequestMapper.map(suppression)).getId();
    }

    public void patchSuppressionResource(Suppression suppression, SuppressionPatchRequest suppressionPatchRequest) {

        mapPatchRequestToSuppression(suppression, suppressionPatchRequest);

        suppressionRepository.save(this.suppressionMapper.map(suppression));
    }

    private void mapPatchRequestToSuppression(Suppression suppression, SuppressionPatchRequest suppressionPatchRequest) {

        if (suppressionPatchRequest.getApplicantDetails() != null) {
            suppression.setApplicantDetails(suppressionPatchRequest.getApplicantDetails());
        }

        if (suppressionPatchRequest.getAddressToRemove() != null) {
            suppression.setAddressToRemove(suppressionPatchRequest.getAddressToRemove());
        }

        if (suppressionPatchRequest.getServiceAddress() != null) {
            suppression.setServiceAddress(suppressionPatchRequest.getServiceAddress());
        }

        if (suppressionPatchRequest.getDocumentDetails() != null) {
            suppression.setDocumentDetails(suppressionPatchRequest.getDocumentDetails());
        }

        if (suppressionPatchRequest.getContactAddress() != null) {
            suppression.setContactAddress(suppressionPatchRequest.getContactAddress());
        }
    }

    public Optional<Suppression> getSuppression(String applicationReference) {
        return suppressionRepository.findById(applicationReference).map(this.suppressionMapper::map);
    }

    public boolean isExistingSuppressionID(String applicationReference) {
        return suppressionRepository.findById(applicationReference).isPresent();
    }
    
    public String generateUniqueSuppressionReference(){
        String randomReference = ReferenceGenerator.generate();
        while(isExistingSuppressionID(randomReference)) {
            randomReference = ReferenceGenerator.generate();
        }
        return randomReference;
    }
}
