package uk.gov.companieshouse.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.gov.companieshouse.database.entity.SuppressionEntity;

import java.util.Optional;

@Repository
public interface SuppressionRepository extends MongoRepository<SuppressionEntity, String> {

    Optional<SuppressionEntity> findById(String applicationReference);

}
