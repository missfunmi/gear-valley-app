package com.gearvalley.scheduler;

import com.gearvalley.SitesConfig;
import com.gearvalley.SitesConfig.Site;
import com.gearvalley.domain.PriceWatchService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceChecker {
  private final PriceWatchService priceWatchService;
  private final Site rei;

  @Autowired
  public PriceChecker(PriceWatchService priceWatchService, SitesConfig sitesConfig) {
    this.priceWatchService = priceWatchService;
    this.rei = sitesConfig.getRei();
  }

  @Scheduled(cron = "${application.scheduler.price-check.cron}")
  public void checkLatestPriceForAllActiveWatches() {
    log.info("PriceChecker task reporting for duty!");

    var activeWatches = priceWatchService.fetchAllActiveWatches();
    log.info("Found {} active watches in the database", activeWatches.size());

    activeWatches.forEach(
        watch -> {
          log.info("Checking latest price for watch={}", watch);
          if (rei.getProviderId().equals(watch.getProviderId())) {
            log.info("Checking latest price at url={}", rei.getBaseSite() + "/" + watch.getUrl());
          }
        });
  }
}
