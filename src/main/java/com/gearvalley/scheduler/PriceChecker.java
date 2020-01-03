package com.gearvalley.scheduler;

import com.gearvalley.SitesConfig;
import com.gearvalley.SitesConfig.Site;
import com.gearvalley.domain.PriceWatchService;
import com.gearvalley.domain.models.PriceWatchUpdateRequest;
import com.gearvalley.infrastructure.GearPriceCheckClient;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceChecker {
  private final PriceWatchService priceWatchService;
  private final GearPriceCheckClient priceCheckClient;
  private final Site rei;

  @Autowired
  public PriceChecker(
      PriceWatchService priceWatchService,
      SitesConfig sitesConfig,
      GearPriceCheckClient priceCheckClient) {
    this.priceWatchService = priceWatchService;
    this.priceCheckClient = priceCheckClient;
    this.rei = sitesConfig.getRei();
  }

  @Scheduled(cron = "${application.scheduler.price-check.cron}")
  public void checkLatestPriceForAllActiveWatches() {
    log.info("PriceChecker task reporting for duty!");

    var activeWatches = priceWatchService.fetchAllActiveWatches();
    log.info("Found {} active watches in the database", activeWatches.size());

    activeWatches.forEach(
        watch -> {
          String watchId = watch.getWatchId();

          if (rei.getProviderId().equals(watch.getProviderId())) {

            // Construct URL
            String url = rei.getBaseSite() + watch.getUrl();
            log.info("Checking latest price at url={} for watch={}", url, watch);

            // Find latest price
            BigDecimal currentPrice = watch.getCurrentPrice().getPrice();
            BigDecimal newPrice = priceCheckClient.searchForPriceAtUrl(url).getPrice();

            // Update price history if price is different
            if (currentPrice.compareTo(newPrice) != 0) {
              log.info(
                  "watchId={} has newPrice={} which is different from currentPrice={}, will update price history",
                  watchId,
                  newPrice,
                  currentPrice);
              PriceWatchUpdateRequest updateRequest =
                  PriceWatchUpdateRequest.builder()
                      .watchId(watchId)
                      .newPrice(newPrice)
                      .keepActive(true)
                      .build();
              var updated = priceWatchService.updatePriceWatch(updateRequest);
              String logger =
                  updated.isPresent()
                      ? "Successfully updated price watch for watchId=%s"
                      : "No watch found for watchId=%s, has it been deleted meanwhile?";
              log.info(String.format(logger, watchId));
            }
          }
        });
  }
}
