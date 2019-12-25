package com.gearvalley.infrastructure;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("dev")
public class MockJsoupClient implements JsoupClient {

  Map<String, String> providerIdToMockSearchResultsFilePath =
      ImmutableMap.of("REI", "mocks/rei-search-results.html");

  @Override
  public Document fetchHTMLDocument(String providerId, String keyword) {
    Preconditions.checkArgument(
        StringUtils.isNotEmpty(providerId),
        "Invalid attempt to locate mock search results file for empty providerId!");
    Preconditions.checkArgument(
        providerIdToMockSearchResultsFilePath.containsKey(providerId),
        "Invalid attempt to locate mock search results file for unknown providerId=%s!",
        providerId);

    try {
      URL resourcePath =
          Resources.getResource(providerIdToMockSearchResultsFilePath.get(providerId));
      String html = Resources.toString(resourcePath, StandardCharsets.UTF_8);
      log.info("Successfully fetched html from resourcePath={}", resourcePath);
      return Jsoup.parse(html, String.format(providerIdToSiteUrlBase.get(providerId), keyword));
    } catch (IOException e) {
      throw new RuntimeException("Something bad happened while Jsouping the request", e);
    }
  }
}
