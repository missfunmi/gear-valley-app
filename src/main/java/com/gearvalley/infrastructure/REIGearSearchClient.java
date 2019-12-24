package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.SearchResult;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class REIGearSearchClient extends ParserBasedGearSearchClient {

  private String siteUrlBase = "https://www.rei.com/search?q=%s&pagesize=90";
  private static final String SEARCH_RESULTS_ID = "search-results";

  @Override
  public SearchResult searchForGearByKeyword(String keyword) {
    Elements elements = searchForGear(keyword);

    // Convert each Element to a Gear

    // Populate SearchResult

    return null;
  }

  @Override
  BigDecimal extractGearPrice(Element element) {
    return null;
  }

  @Override
  String extractGearDirectUrl(Element element) {
    return null;
  }

  @Override
  String extractGearBase64Image(Element element) {
    return null;
  }

  @Override
  String extractGearTitle(Element element) {
    return null;
  }

  @Override
  String extractGearSize(Element element) {
    return null;
  }
}