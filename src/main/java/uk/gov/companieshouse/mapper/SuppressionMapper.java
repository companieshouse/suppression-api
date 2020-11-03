package uk.gov.companieshouse.mapper;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.database.entity.DocumentDetailsEntity;
import uk.gov.companieshouse.database.entity.SuppressionEntity;
import uk.gov.companieshouse.mapper.base.Mapper;
import uk.gov.companieshouse.model.Suppression;

@Component
public class SuppressionMapper implements Mapper<SuppressionEntity, Suppression> {

    private final ApplicantDetailsMapper applicantDetailsMapper;
    private final AddressMapper addressMapper;
    private final DocumentDetailsMapper documentDetailsMapper;
    private final PaymentDetailsMapper paymentDetailsMapper;

    public SuppressionMapper(ApplicantDetailsMapper applicantDetailsMapper,
                             AddressMapper addressMapper,
                             DocumentDetailsMapper documentDetailsMapper,
                             PaymentDetailsMapper paymentDetailsMapper) {

        this.applicantDetailsMapper = applicantDetailsMapper;
        this.addressMapper = addressMapper;
        this.documentDetailsMapper = documentDetailsMapper;
        this.paymentDetailsMapper = paymentDetailsMapper;
    }

    @Override
    public SuppressionEntity map(Suppression value) {
        if (value == null) {
            return null;
        }
        return new SuppressionEntity(
            value.getApplicationReference(),
            value.getCreatedAt(),
            applicantDetailsMapper.map(value.getApplicantDetails()),
            addressMapper.map(value.getAddressToRemove()),
            addressMapper.map(value.getServiceAddress()),
            new DocumentDetailsEntity[] {
                documentDetailsMapper.map(value.getDocumentDetails())
            },
            addressMapper.map(value.getContactAddress()),
            value.getEtag(),
            paymentDetailsMapper.map(value.getPaymentDetails())
        );
    }

    @Override
    public Suppression map(SuppressionEntity value) {
        if (value == null) {
            return null;
        }
        return new Suppression(
            value.getCreatedAt(),
            value.getId(),
            applicantDetailsMapper.map(value.getApplicantDetails()),
            addressMapper.map(value.getAddressToRemove()),
            addressMapper.map(value.getServiceAddress()),
            documentDetailsMapper.map(value.getDocumentDetails()[0]),
            addressMapper.map(value.getContactAddress()),
            value.getEtag(),
            paymentDetailsMapper.map(value.getPaymentDetails())
        );
    }
}
