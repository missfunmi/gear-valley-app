package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.GearImage;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("prod")
public class DefaultJsoupClient implements JsoupClient {

  // TODO Get rid of this method
  @Override
  public Document fetchHTMLDocument(String providerId, String keyword) {
    Preconditions.checkArgument(
        StringUtils.isNotEmpty(providerId), "Invalid attempt to search with empty providerId!");
    Preconditions.checkArgument(
        providerIdToSiteUrlBase.containsKey(providerId),
        "Invalid attempt to search with unknown providerId=%s!",
        providerId);
    Preconditions.checkArgument(
        StringUtils.isNotEmpty(keyword), "Invalid attempt to search with empty keyword!");

    // TODO "/search?q=" is REI-specific -- should generify this implementation
    String url = providerIdToSiteUrlBase.get(providerId) + "/search?q=" + keyword + "&pagesize=90";

    return fetchHTMLDocument(url);
  }

  @Override
  public Document fetchHTMLDocument(String url) {
    try {
      return Jsoup.connect(url)
          .timeout(30000)
          .userAgent("Mozilla/5.0")
          .ignoreHttpErrors(true)
          .get();
    } catch (IOException e) {
      throw new RuntimeException("Something bad happened while Jsouping the request", e);
    }
  }

  @Override
  public GearImage fetchGearImage(String providerId, String relativePath) {
    Preconditions.checkArgument(
        StringUtils.isNotEmpty(providerId), "Invalid attempt to search with empty providerId!");
    Preconditions.checkArgument(
        providerIdToSiteUrlBase.containsKey(providerId),
        "Invalid attempt to search with unknown providerId=%s!",
        providerId);
    Preconditions.checkArgument(
        StringUtils.isNotEmpty(relativePath), "Invalid attempt to search with empty relativePath!");

    // TODO "relativePath" implementation may be REI-specific, other sites might have embedded
    // images or svg, etc.
    String url = providerIdToSiteUrlBase.get(providerId) + "/" + relativePath;

    try {
      Response resultImageResponse =
          Jsoup.connect(url)
              .timeout(30000)
              .userAgent("Mozilla/5.0")
              .ignoreContentType(true)
              .execute();
      String base64Image = Base64.getEncoder().encodeToString(resultImageResponse.bodyAsBytes());
      String contentType = resultImageResponse.contentType();
      return GearImage.builder().base64Image(base64Image).contentType(contentType).build();
    } catch (IOException e) {
      throw new RuntimeException("Something bad happened while Jsouping the request", e);
    }
  }
}
