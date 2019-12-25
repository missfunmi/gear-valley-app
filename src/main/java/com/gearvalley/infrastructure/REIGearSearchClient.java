package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.Gear;
import com.gearvalley.domain.models.SearchResult;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class REIGearSearchClient extends ParserBasedGearSearchClient {

  private static final String PROVIDER_ID = "REI";
  private static final String SEARCH_RESULTS_ID = "search-results";

  @Autowired
  public REIGearSearchClient(JsoupClient jsoupClient) {
    super(jsoupClient);
  }

  @Override
  public SearchResult searchForGearByKeyword(String keyword) {
    List<Gear> gear = searchForGear(PROVIDER_ID, keyword);
    log.info("Searched by keyword={} at source={} and found {} gear={}", keyword, PROVIDER_ID, gear.size(), gear);
    
    return SearchResult.builder()
        .providerId(PROVIDER_ID) // TODO -- should be a UUID?
        .providerName(PROVIDER_ID)
        .gear(gear)
        .providerHomePage("https://rei.com") // TODO -- Rename this field
        .build();
  }

  @Override
  Elements filterToSearchResults(Document document) {
    try {
      return document.getElementById(SEARCH_RESULTS_ID).getElementsByTag("ul").get(0).children();
    } catch (Exception e) {
      log.warn("An error might have occurred, or there were no search results available");
      return new Elements();
    }
  }

  @Override
  BigDecimal extractGearPrice(Element element) {
    String price =
        element
            .select("li > a div:has(span)")
            .get(0)
            .select("span:not(:has(*)):contains($)")
            .get(0)
            .text();
    log.info("Extracted price={}", price);
    String replaced = StringUtils.remove(price, '$');
    return new BigDecimal(replaced);
  }

  @Override
  String extractGearDirectUrl(Element element) {
    String directUrl =
        element
            .select("li > a")
            .get(0)
            .attr("href");
    log.info("Extracted directUrl={}", directUrl);
    return directUrl;
  }

  @Override
  String extractGearImageSrc(Element element) {
    String relativePathToImage =
        element
            .select("li > a > div img")
            .get(0)
            .attr("src");
    log.info("Extracted relativePathToImage={}", relativePathToImage);
    return relativePathToImage;
  }

  @Override
  String extractGearTitle(Element element) {
    String title =
        element
            .select("li > a > h2 > div")
            .text();
    log.info("Extracted title={}", title);
    return title;
  }

  @Override
  String extractGearDescription(Element element) {
    String description =
        element
            .select("li > a > span")
            .text();
    log.info("Extracted description={}", description);
    return description;
  }
}
