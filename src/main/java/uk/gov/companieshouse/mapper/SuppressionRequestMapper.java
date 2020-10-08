package uk.gov.companieshouse.mapper;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.mapper.base.Mapper;
import uk.gov.companieshouse.model.SuppressionRequest;

@Component
public class SuppressionRequestMapper implements Mapper<SuppressionEntity, SuppressionRequest> {

    private final ApplicantDetailsMapper applicantDetailsMapper;

    public SuppressionRequestMapper(ApplicantDetailsMapper applicantDetailsMapper) {

        this.applicantDetailsMapper = applicantDetailsMapper;
    }

    @Override
    public SuppressionEntity map(SuppressionRequest value) {
        if (value == null) {
            return null;
        }
        return new SuppressionEntity(
            value.getApplicationReference(),
            value.getCreatedAt(),
            applicantDetailsMapper.map(value.getApplicantDetails()),
            null,
            null,
            null,
            null,
            value.getEtag()
        );
    }

    @Override
    public SuppressionRequest map(SuppressionEntity value) {
        if (value == null) {
            return null;
        }
        return new SuppressionRequest(
            value.getCreatedAt(),
            value.getId(),
            applicantDetailsMapper.map(value.getApplicantDetails()),
            value.getEtag()
        );
    }
}
