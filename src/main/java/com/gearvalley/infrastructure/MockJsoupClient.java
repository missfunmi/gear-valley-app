package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.GearImage;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

  Map<String, String> providerIdToMockPriceCheckFilePath =
      ImmutableMap.of("REI", "mocks/rei-price-check-outlet.html");

  Map<String, String> providerIdToMockSearchResultsImageFilePath =
      ImmutableMap.of("REI", "mocks/rei-search-results-image.jpeg");

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
      throw new RuntimeException("Something bad happened while Jsoup-parsing the mock results file", e);
    }
  }

  @Override
  public Document fetchHTMLDocument(String url) {

    try {
      // TODO Clean this up
      URL resourcePath =
          Resources.getResource(providerIdToMockPriceCheckFilePath.get("REI"));
      String html = Resources.toString(resourcePath, StandardCharsets.UTF_8);
      log.info("Successfully fetched html from resourcePath={}", resourcePath);
      return Jsoup.parse(html, url);
    } catch (IOException e) {
      throw new RuntimeException(
          "Something bad happened while Jsoup-parsing the mock results file", e);
    }
  }

  @Override
  public GearImage fetchGearImage(String providerId, String relativePath) {
    Preconditions.checkArgument(
        StringUtils.isNotEmpty(providerId), "Invalid attempt to locate mock image file for empty providerId!");
    Preconditions.checkArgument(
        providerIdToSiteUrlBase.containsKey(providerId),
        "Invalid attempt to locate mock image file for unknown providerId=%s!",
        providerId);

    try {
      URL resourcePath =
          Resources.getResource(providerIdToMockSearchResultsImageFilePath.get(providerId));
      byte[] image = Resources.toByteArray(resourcePath);
      String base64Image = Base64.getEncoder().encodeToString(image);
      return GearImage.builder().base64Image(base64Image).contentType("image/jpeg;charset=utf-8").build();
    } catch (IOException e) {
      throw new RuntimeException("Something bad happened while fetching the mock image file", e);
    }
  }
}
