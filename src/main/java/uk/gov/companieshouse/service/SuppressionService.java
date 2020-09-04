package uk.gov.companieshouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.mapper.SuppressionMapper;
import uk.gov.companieshouse.model.Suppression;
import uk.gov.companieshouse.repository.SuppressionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class SuppressionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuppressionService.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final SuppressionMapper suppressionMapper;
    private final SuppressionRepository suppressionRepository;

    @Autowired
    public SuppressionService(SuppressionMapper suppressionMapper, SuppressionRepository suppressionRepository) {
        this.suppressionMapper = suppressionMapper;
        this.suppressionRepository = suppressionRepository;
    }

    public String saveSuppression(Suppression suppression) {
        suppression.setCreatedAt(LocalDateTime.now());

        String suppressionId = createSuppressionInMongoDB(suppression);

        suppression.setId(suppressionId);

        return suppressionId;

    }

    private String createSuppressionInMongoDB(Suppression suppression) {
        return Optional.ofNullable(suppressionRepository.insert(this.suppressionMapper.map(suppression))).map(SuppressionEntity::getId).orElseThrow(() ->
            new RuntimeException(String.format("Suppression not saved in database for companyNumber: %s", suppression.getDocumentDetails().getCompanyNumber())));
    }
}
