package com.gearvalley.infrastructure;

import com.google.common.base.Preconditions;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("production")
public class DefaultJsoupClient implements JsoupClient {

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

    try {
      // TODO "/search?q=" is REI-specific -- should generify this implementation
      String url =
          providerIdToSiteUrlBase.get(providerId) + "/search?q=" + keyword + "&pagesize=90";
      return Jsoup.connect(url)
          .timeout(30000)
          .userAgent("Mozilla/5.0")
          .ignoreHttpErrors(true)
          .get();
    } catch (IOException e) {
      throw new RuntimeException("Something bad happened while Jsouping the request", e);
    }
  }
}
