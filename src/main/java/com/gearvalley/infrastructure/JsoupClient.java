package com.gearvalley.infrastructure;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.jsoup.nodes.Document;

public interface JsoupClient {

  // TODO - value should be an object to hold other required fields
  // e.g. the search sub-resource ("/search?q=%s&pagesize=90" for REI), etc.
  Map<String, String> providerIdToSiteUrlBase = ImmutableMap.of("REI", "https://www.rei.com");

  Document fetchHTMLDocument(String providerId, String keyword);
}
