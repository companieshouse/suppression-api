package uk.gov.companieshouse.service.suppression;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.repository.SuppressionRepository;

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

        return createSuppressionInMongoDB(suppression);
    }

    public boolean isExistingSuppressionID(String applicationReference) {
        return suppressionRepository.findById(applicationReference).isPresent();
    }

    public void deleteSuppressionByReference(String applicationReference) {
        suppressionRepository.deleteById(applicationReference);
    }

    public String generateUniqueSuppressionReference(){

        String randomReference = generateRandomSequence();
        while(isExistingSuppressionID(randomReference)) {
            randomReference = generateRandomSequence();
        }

        return randomReference;
    }

    private String createSuppressionInMongoDB(Suppression suppression) {
        return suppressionRepository.insert(this.suppressionMapper.map(suppression)).getId();
    }

    private String generateRandomSequence() {

        StringBuilder builder = new StringBuilder();
        return builder
            .append(RandomStringUtils.random(5, true, true).toUpperCase())
            .append('-')
            .append(RandomStringUtils.random(5, true, true).toUpperCase())
            .toString();
    }
}
