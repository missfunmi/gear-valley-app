package com.gearvalley.domain;

import com.gearvalley.domain.models.PriceWatchAddRequest;
import com.gearvalley.domain.models.PriceWatch;
import java.util.List;
import java.util.Optional;

public interface PriceWatchService {

  PriceWatch addWatch(PriceWatchAddRequest priceWatchAddRequest);

  List<PriceWatch> fetchAllWatches();

  List<PriceWatch> fetchWatchesByProviderIdAndUrl(String providerId, String url);

  Optional<PriceWatch> fetchWatchById(String watchId);

  Optional<PriceWatch> activateWatch(String watchId);

  Optional<PriceWatch> deActivateWatch(String watchId);

  Optional<PriceWatch> deleteWatch(String watchId);
}
