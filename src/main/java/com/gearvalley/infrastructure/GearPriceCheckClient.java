package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.PriceCheckResult;

public interface GearPriceCheckClient {

  PriceCheckResult searchForPriceAtUrl(String url);

}
