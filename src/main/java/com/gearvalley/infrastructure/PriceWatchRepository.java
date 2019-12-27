package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.PriceWatch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceWatchRepository extends MongoRepository<PriceWatch, String> {

  Optional<PriceWatch> findByWatchId(String watchId);

  List<PriceWatch> deleteByWatchId(String watchId);
}
