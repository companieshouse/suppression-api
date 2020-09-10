package uk.gov.companieshouse.mapper;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.mapper.base.Mapper;
import uk.gov.companieshouse.model.DocumentDetails;

@Component
public class DocumentDetailsMapper implements Mapper<DocumentDetailsEntity, DocumentDetails> {

    @Override
    public DocumentDetailsEntity map(DocumentDetails value) {
        if (value == null) {
            return null;
        }
        return new DocumentDetailsEntity(
            value.getCompanyName(),
            value.getCompanyNumber(),
            value.getDescription(),
            value.getDate()
        );
    }

    @Override
    public DocumentDetails map(DocumentDetailsEntity value) {
        if (value == null) {
            return null;
        }
        return new DocumentDetails(
            value.getCompanyName(),
            value.getCompanyNumber(),
            value.getDescription(),
            value.getDate()
        );
    }
}
