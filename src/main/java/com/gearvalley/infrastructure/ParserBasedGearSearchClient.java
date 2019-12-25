package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.Gear;
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

  Elements searchForGear(String providerId, String keyword, String searchResultsId) {
    Document document = jsoupClient.fetchHTMLDocument(providerId, keyword);

    // TODO -- searchResultsId is impl specific, need to rewrite
    if (document == null || document.getElementById(searchResultsId) == null) {
      log.warn(
          "VVVVVVVVVVVV Error might have occurred, or there were no search results available for keyword={}",
          keyword);
      return new Elements();
    }

    log.info(
        "VVVVVVVVVVVV Completed Jsoup parsing of uri={} and retrieved document={}",
        document.location(),
        document);

    // TODO -- Impl specific, need to rewrite
    Elements searchResults =
        document.getElementById(searchResultsId).getElementsByTag("ul").get(0).children();

    log.info(
        "VVVVVVVVVVVV Successfully fetched {} results for keyword={}. Results are: \n\n\n{}",
        searchResults.size(),
        keyword,
        searchResults);

    return searchResults;
  }

  abstract Gear extractGearPrice(Element element);

  abstract Gear extractGearDirectUrl(Element element);

  abstract Gear extractGearBase64Image(Element element);

  abstract Gear extractGearTitle(Element element);

  abstract Gear extractGearSize(Element element);
}
