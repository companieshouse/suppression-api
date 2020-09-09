package uk.gov.companieshouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.repository.SuppressionRepository;
import uk.gov.companieshouse.utils.ReferenceGenerator;

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
        return suppressionRepository.save(this.suppressionMapper.map(suppression)).getId();
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
