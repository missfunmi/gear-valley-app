package com.gearvalley.scheduler;

import com.gearvalley.domain.models.PriceWatch;

public interface Notifier {

  void notifyUpdatedPriceWatch(PriceWatch priceWatch);

}
