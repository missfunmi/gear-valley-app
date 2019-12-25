package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.Gear;
import org.jsoup.nodes.Element;

// @Mapper
public interface GearMapper {

  /**
   * @Mappings({ @Mapping(target="", source="") })
   *
   * <p>url = li > a.href --> returns relative path (e.g.
   * "/rei-garage/product/163667/marmot-precip-eco-jacket-womens" base64Image = li > a > div >
   * img.src --> returns relative path to JPEG image (e.g. "/media/product/163667") title = li > a >
   * h2 > div.text (or innerHTML) --> e.g. "Marmot" + li > a > h2 > div.text (or innerHTML) --> e.g.
   * "PreCip Eco Jacket - Women's" (i.e. concatenate all) metadata = li > a > span --> (e.g. "6
   * colors available") price = li > a > div > span.text (or innerHTML);
   *
   * <p>Other useful fields li > div > div > svg (data-ui="product-badge-garage") --> returns REI
   * OUTLET logo
   *
   * <p>brand = description = title = brand + space + description
   */
  Gear toGear(Element listItem);
}
