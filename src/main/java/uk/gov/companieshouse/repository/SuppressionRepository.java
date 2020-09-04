package uk.gov.companieshouse.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.gov.companieshouse.database.entity.SuppressionEntity;

@Repository
public interface SuppressionRepository extends MongoRepository<SuppressionEntity, String> {

    SuppressionEntity insert(SuppressionEntity suppression);

}
