package com.gearvalley.infrastructure;

import com.gearvalley.domain.PriceWatchService;
import com.gearvalley.domain.models.Gear;
import com.gearvalley.domain.models.PriceWatch;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public abstract class ParserBasedGearSearchClient implements GearSiteSearchClient {

  private final JsoupClient jsoupClient;
  private final PriceWatchService priceWatchService;

  public ParserBasedGearSearchClient(JsoupClient jsoupClient, PriceWatchService priceWatchService) {
    this.jsoupClient = jsoupClient;
    this.priceWatchService = priceWatchService;
  }

  List<Gear> searchForGear(String providerId, String keyword) {
    var document = jsoupClient.fetchHTMLDocument(providerId, keyword);
    var matchingElements = filterToSearchResults(document);

    return matchingElements.stream()
        .map(
            x -> {
              String url = extractGearDirectUrl(x);
              Optional<PriceWatch> matchingPriceWatch =
                  findSingleMatchingPriceWatch(providerId, url);

              return Gear.builder()
                  .price(extractGearPrice(x))
                  .title(extractGearTitle(x))
                  .description(extractGearDescription(x))
                  .url(url)
                  .watchId(matchingPriceWatch.map(PriceWatch::getWatchId).orElse(null))
                  .isWatchActive(matchingPriceWatch.map(PriceWatch::isActive).orElse(null))
                  .image(jsoupClient.fetchGearImage(providerId, extractGearImageSrc(x)))
                  .build();
            })
        .collect(Collectors.toList());
  }

  Optional<PriceWatch> findSingleMatchingPriceWatch(String providerId, String url) {
    var priceWatches = priceWatchService.fetchWatchesByProviderIdAndUrl(providerId, url);
    if (priceWatches.size() <= 1) {
      log.info(
          "Found {} priceWatch for the given providerId={} and url={}",
          priceWatches.size(),
          providerId,
          url);
    } else {
      log.warn(
          "Unexpectedly found more than 1 priceWatch for the given providerId={} and url={}, returning the first one",
          providerId,
          url);
    }
    return priceWatches.isEmpty() ? Optional.empty() : Optional.of(priceWatches.get(0));
  }

  abstract Elements filterToSearchResults(Document document);

  abstract BigDecimal extractGearPrice(Element element);

  abstract String extractGearDirectUrl(Element element);

  abstract String extractGearImageSrc(Element element);

  abstract String extractGearTitle(Element element);

  abstract String extractGearDescription(Element element);
}
