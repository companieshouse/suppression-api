package uk.gov.companieshouse.service;

import org.slf4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.GenerateEtagUtil;
import uk.gov.companieshouse.controller.SuppressionController;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.repository.SuppressionRepository;
import uk.gov.companieshouse.utils.ReferenceGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SuppressionService {

    private final SuppressionMapper suppressionMapper;
    private final SuppressionRepository suppressionRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SuppressionController.class);

    @Autowired
    public SuppressionService(SuppressionMapper suppressionMapper, SuppressionRepository suppressionRepository) {
        this.suppressionMapper = suppressionMapper;
        this.suppressionRepository = suppressionRepository;
    }

    public String saveSuppression(Suppression suppression) {

        if (StringUtils.isBlank(suppression.getApplicationReference())) {

            String generatedReference = generateUniqueSuppressionReference();
            suppression.setApplicationReference(generatedReference);

            LOGGER.info("No application reference found, generated {}", suppression.getApplicationReference());
        }

        suppression.setEtag(GenerateEtagUtil.generateEtag());
        suppression.setCreatedAt(LocalDateTime.now());
        return suppressionRepository.save(this.suppressionMapper.map(suppression)).getId();
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
