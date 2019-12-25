package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.Gear;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public abstract class ParserBasedGearSearchClient implements GearSiteSearchClient {

  private JsoupClient jsoupClient;

  public ParserBasedGearSearchClient(JsoupClient jsoupClient) {
    this.jsoupClient = jsoupClient;
  }

  List<Gear> searchForGear(String providerId, String keyword) {
    Document document = jsoupClient.fetchHTMLDocument(providerId, keyword);
    Elements matchingElements = filterToSearchResults(document);

    return matchingElements.stream()
        .map(
            x ->
                Gear.builder()
                    .price(extractGearPrice(x))
                    .title(extractGearTitle(x))
                    .url(extractGearDirectUrl(x))
                    .image(jsoupClient.fetchGearImage(providerId, extractGearImageSrc(x)))
                    .size(extractGearSize(x))
                    .build())
        .collect(Collectors.toList());
  }

  abstract Elements filterToSearchResults(Document document);

  abstract BigDecimal extractGearPrice(Element element);

  abstract String extractGearDirectUrl(Element element);

  abstract String extractGearImageSrc(Element element);

  abstract String extractGearTitle(Element element);

  abstract String extractGearSize(Element element);
}
