package com.gearvalley.infrastructure;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Slf4j
public abstract class ParserBasedGearPriceCheckClient implements GearPriceCheckClient {

  private final JsoupClient jsoupClient;

  public ParserBasedGearPriceCheckClient(
      JsoupClient jsoupClient) {
    this.jsoupClient = jsoupClient;
  }

  BigDecimal checkLatestPriceForPriceWatch(String url) {
    var document = jsoupClient.fetchHTMLDocument(url);
    var element = filterToGearDetail(document);
    return extractGearPrice(element);
  }

  abstract Element filterToGearDetail(Document document);

  abstract BigDecimal extractGearPrice(Element element);

}
