package uk.gov.companieshouse.mapper;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.database.entity.ApplicantDetailsEntity;
import uk.gov.companieshouse.mapper.base.Mapper;
import uk.gov.companieshouse.model.ApplicantDetails;

@Component
public class ApplicantDetailsMapper implements Mapper<ApplicantDetailsEntity, ApplicantDetails> {

    @Override
    public ApplicantDetailsEntity map(ApplicantDetails value) {
        if (value == null) {
            return null;
        }
        return new ApplicantDetailsEntity(
            value.getFullName(),
            value.getPreviousName(),
            value.getEmailAddress()
        );
    }

    @Override
    public ApplicantDetails map(ApplicantDetailsEntity value) {
        if (value == null) {
            return null;
        }
        return new ApplicantDetails(
            value.getFullName(),
            value.getPreviousName(),
            value.getEmailAddress()
        );
    }
}
