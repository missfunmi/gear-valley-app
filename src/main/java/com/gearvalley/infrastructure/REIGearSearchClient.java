package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.Gear;
import com.gearvalley.domain.models.SearchResult;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
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
    Elements elements = searchForGear(PROVIDER_ID, keyword, SEARCH_RESULTS_ID);

    return SearchResult.builder()
        .providerId(PROVIDER_ID) // TODO -- should be a UUID?
        .providerName(PROVIDER_ID)
        .gear(Lists.newArrayList()) // TODO -- Convert each Element above to a Gear
        .providerHomePage("https://rei.com") // TODO -- Could likely get rid of this field...
        .build();
  }

  @Override
  Gear extractGearPrice(Element element) {
    return null;
  }

  @Override
  Gear extractGearDirectUrl(Element element) {
    return null;
  }

  @Override
  Gear extractGearBase64Image(Element element) {
    return null;
  }

  @Override
  Gear extractGearTitle(Element element) {
    return null;
  }

  @Override
  Gear extractGearSize(Element element) {
    return null;
  }
}
