package com.gearvalley.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearvalley.domain.models.PriceCheckResult;
import java.math.BigDecimal;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class REIGearPriceCheckClient extends ParserBasedGearPriceCheckClient {

  private static final String REI_OUTLET_PREFIX = "/rei-garage";
  private static final String OUTLET_GEAR_DETAIL_ID = "page-data";
  private static final String MAIN_GEAR_DETAIL_ID = "page-content";
  private static final String MAIN_GEAR_DETAIL_SUBSELECTOR =
      "script[data-client-store=product-price-data]";
  private final ObjectMapper objectMapper;

  @Autowired
  public REIGearPriceCheckClient(JsoupClient jsoupClient, ObjectMapper objectMapper) {
    super(jsoupClient);
    this.objectMapper = objectMapper;
  }

  @Override
  public PriceCheckResult searchForPriceAtUrl(String url) {
    BigDecimal latestPrice = checkLatestPriceForPriceWatch(url);
    log.info("Ran price check for url={} and found latestPrice={}", url, latestPrice);
    return PriceCheckResult.builder().price(latestPrice).build();
  }

  @Override
  Element filterToGearDetail(Document document) {
    if (document.location().contains(REI_OUTLET_PREFIX)) {
      return document.getElementById(OUTLET_GEAR_DETAIL_ID);
    } else {
      return document
          .getElementById(MAIN_GEAR_DETAIL_ID)
          .select(MAIN_GEAR_DETAIL_SUBSELECTOR)
          .get(0);
    }
  }

  @Override
  BigDecimal extractGearPrice(Element element) {
    String html = element.html();
    try {
      HashMap possibleJson = objectMapper.readValue(html, HashMap.class);

      Double extractedPrice;
      if (element.baseUri().contains(REI_OUTLET_PREFIX)) {
        extractedPrice =
            (Double)
                ((HashMap) ((HashMap) possibleJson.get("product")).get("displayPrice")).get("min");
      } else {
        extractedPrice = (Double) possibleJson.get("min");
      }

      String price = String.format("%.2f", extractedPrice);
      log.info("Extracted latest price={} for url={}", price, element.baseUri());
      return new BigDecimal(price);
    } catch (Exception e) {
      throw new RuntimeException(
          String.format("Error reading or parsing attributes from HTML element=%s", element), e);
    }
  }
}
