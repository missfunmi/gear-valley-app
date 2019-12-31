package com.gearvalley.domain;

import com.gearvalley.domain.models.PriceWatch;
import com.gearvalley.domain.models.PriceWatchAddRequest;
import com.gearvalley.domain.models.PriceWatchUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface PriceWatchService {

  PriceWatch addWatch(PriceWatchAddRequest priceWatchAddRequest);

  Optional<PriceWatch> updatePriceWatch(PriceWatchUpdateRequest priceWatchUpdateRequest);

  List<PriceWatch> fetchAllWatches();

  List<PriceWatch> fetchAllActiveWatches();

  List<PriceWatch> fetchWatchesByProviderIdAndUrl(String providerId, String url);

  Optional<PriceWatch> fetchWatchById(String watchId);

  Optional<PriceWatch> activateWatch(String watchId);

  Optional<PriceWatch> deactivateWatch(String watchId);

  Optional<PriceWatch> deleteWatch(String watchId);
}
