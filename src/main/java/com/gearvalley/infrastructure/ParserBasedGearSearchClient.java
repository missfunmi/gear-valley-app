package com.gearvalley.infrastructure;

import java.io.IOException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public abstract class ParserBasedGearSearchClient implements GearSiteSearchClient {

  Elements searchForGear(String siteUrlBase, String searchKeyword, String searchResultsId) {
    
    if (StringUtils.isEmpty(searchKeyword)) {
      log.warn(
          "Invalid attempt to search by empty string! Was this intentional? Returning empty list");
      return new Elements();
    }

    try {
      Document document = Jsoup.connect(String.format(siteUrlBase, searchKeyword))
          .timeout(30000)            // TODO - move to config
          .userAgent("Mozilla/5.0")  // TODO - move to config
          .ignoreHttpErrors(true)    // TODO - better error handling?
          .get();

      if (document == null || document.getElementById(searchResultsId) == null) {
        log.info("Error occurred, or no search results available for keyword={}", searchKeyword);
        return new Elements();
      }

      // TODO -- Maybe make this more defensive?
      Elements searchResults =
          document.getElementById(searchResultsId).getElementsByTag("ul").get(0).children();

      log.info("Successfully fetched {} results for keyword={}. Results are: \n\n\n{}",
          searchResults.size(), searchKeyword, searchResults);

    } catch (IOException e) {
      throw new RuntimeException("Error occurred: ", e)  ;
    }

    return new Elements();
  }

  abstract BigDecimal extractGearPrice(Element element);
  abstract String extractGearDirectUrl(Element element);
  abstract String extractGearBase64Image(Element element);
  abstract String extractGearTitle(Element element);
  abstract String extractGearSize(Element element);

}
