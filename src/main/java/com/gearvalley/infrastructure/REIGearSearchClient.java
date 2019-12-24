package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.SearchResult;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Slf4j
public class REIGearSearchClient implements GearSiteSearchClient {

  private static final String REI_BASE = "https://www.rei.com/search?q=%s&pagesize=90";

  @Override
  public List<SearchResult> searchForGearByKeyword(String keyword) {
    if (StringUtils.isEmpty(keyword)) {
      return Lists.newArrayList();
    }

    try {
      Document doc = Jsoup.connect(String.format(REI_BASE, keyword))
          .timeout(30000)
          .userAgent("Mozilla/5.0")
          .ignoreHttpErrors(true)
          .get();

      if (doc == null) {
        log.info("No search results available for keyword={}", keyword);
        return Lists.newArrayList();
      }

      Elements searchResults =
          doc.getElementById("search-results").getElementsByTag("ul").get(0).children();

      log.info("Successfully fetched {} results: \n\n\n{}", searchResults.size(), searchResults);

    } catch (IOException e) {
      throw new RuntimeException("REI no likey: ", e)  ;
    }

    return null;
  }
}