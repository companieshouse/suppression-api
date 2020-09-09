package uk.gov.companieshouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.repository.SuppressionRepository;
import uk.gov.companieshouse.utils.RandomReferenceSequence;

import java.time.LocalDateTime;

@Service
public class SuppressionService {

    private final SuppressionMapper suppressionMapper;
    private final SuppressionRepository suppressionRepository;

    @Autowired
    public SuppressionService(SuppressionMapper suppressionMapper, SuppressionRepository suppressionRepository) {
        this.suppressionMapper = suppressionMapper;
        this.suppressionRepository = suppressionRepository;
    }

    public String saveSuppression(Suppression suppression) {
        suppression.setCreatedAt(LocalDateTime.now());
        return saveSuppressionInMongoDB(suppression);
    }

    public boolean isExistingSuppressionID(String applicationReference) {
        return suppressionRepository.findById(applicationReference).isPresent();
    }

    public void deleteSuppressionByReference(String applicationReference) {
        suppressionRepository.deleteById(applicationReference);
    }

    public String generateUniqueSuppressionReference(){
        String randomReference = RandomReferenceSequence.generate();
        while(isExistingSuppressionID(randomReference)) {
            randomReference = RandomReferenceSequence.generate();
        }
        return randomReference;
    }

    private String saveSuppressionInMongoDB(Suppression suppression) {
        return suppressionRepository.save(this.suppressionMapper.map(suppression)).getId();
    }
}
