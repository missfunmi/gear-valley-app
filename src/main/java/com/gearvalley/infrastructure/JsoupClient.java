package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.GearImage;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.jsoup.nodes.Document;

public interface JsoupClient {

  // TODO -
  //  1. Use SiteConfig
  //  2. Value should probably be an object to hold other required fields
  //     e.g. the search sub-resource ("/search?q=%s&pagesize=90" for REI), etc.
  Map<String, String> providerIdToSiteUrlBase = ImmutableMap.of("REI", "https://www.rei.com");

  // TODO Get rid of this method
  Document fetchHTMLDocument(String providerId, String keyword);

  Document fetchHTMLDocument(String url);

  GearImage fetchGearImage(String providerId, String relativePath);
}
