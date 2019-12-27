package com.gearvalley.domain;

import com.gearvalley.domain.models.PriceWatchAddRequest;
import com.gearvalley.domain.models.PriceWatch;
import java.util.List;
import java.util.Optional;

public interface PriceWatchService {

  PriceWatch addWatch(PriceWatchAddRequest priceWatchAddRequest);

  List<PriceWatch> fetchAllWatches();

  Optional<PriceWatch> fetchWatchById(String watchId);

  List<PriceWatch> fetchWatchesByProviderIdAndUrl(String providerId, String url);

  PriceWatch deleteWatch(String watchId);
}
