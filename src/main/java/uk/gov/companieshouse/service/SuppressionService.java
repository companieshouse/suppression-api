package uk.gov.companieshouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.GenerateEtagUtil;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.model.ApplicantDetails;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.model.SuppressionPatchRequest;
import uk.gov.companieshouse.model.payment.PaymentPatchRequest;
import uk.gov.companieshouse.model.payment.PaymentStatus;
import uk.gov.companieshouse.repository.SuppressionRepository;
import uk.gov.companieshouse.utils.ReferenceGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SuppressionService {

    private final SuppressionMapper suppressionMapper;
    private final SuppressionRepository suppressionRepository;
    private final EmailService emailService;

    @Autowired
    public SuppressionService(SuppressionMapper suppressionMapper, SuppressionRepository suppressionRepository,
            EmailService emailService) {
        this.suppressionMapper = suppressionMapper;
        this.suppressionRepository = suppressionRepository;
        this.emailService = emailService;
    }

    public String saveSuppression(ApplicantDetails applicantDetails) {

        final Suppression suppression = new Suppression();
        suppression.setApplicantDetails(applicantDetails);
        suppression.setApplicationReference(generateUniqueSuppressionReference());
        suppression.setEtag(GenerateEtagUtil.generateEtag());
        suppression.setCreatedAt(LocalDateTime.now());

        return suppressionRepository.save(this.suppressionMapper.map(suppression)).getId();
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

    public void handlePayment(PaymentPatchRequest data, Suppression suppression) {
        // TODO: Update payment status
        if (data.getStatus() == PaymentStatus.PAID) {
            emailService.sendToStaff(suppression);
        }
    }
    
    public String generateUniqueSuppressionReference(){
        String randomReference = ReferenceGenerator.generate();
        while(isExistingSuppressionID(randomReference)) {
            randomReference = ReferenceGenerator.generate();
        }
        return randomReference;
    }
}
