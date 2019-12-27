package com.gearvalley.domain;

import com.gearvalley.domain.models.PriceWatchAddRequest;
import com.gearvalley.domain.models.PriceWatch;
import java.util.List;
import java.util.Optional;

public interface PriceWatchService {

  PriceWatch addWatch(PriceWatchAddRequest priceWatchAddRequest);

  List<PriceWatch> fetchAllWatches();

  Optional<PriceWatch> fetchWatchById(String watchId);

  PriceWatch deleteWatch(String watchId);
}
