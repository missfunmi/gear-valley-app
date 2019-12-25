package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.Gear;
import org.jsoup.nodes.Element;

// @Mapper
public interface GearMapper {

  /**
   * @Mappings({ @Mapping(target="", source="") })
   *
   * <p>
   *   title = li > a > h2 > div.text (or innerHTML) --> e.g. "Marmot"
   *            + li > a > h2 > div.text (or innerHTML) --> e.g. "PreCip Eco Jacket - Women's" (i.e. concatenate all)
   *   metadata = li > a > span --> (e.g. "6 colors available")
   *
   * <p>Other useful fields:
   *   li > div > div > svg (data-ui="product-badge-garage") --> returns REI OUTLET logo
   *
   * <p>brand = description = title = brand + space + description
   */
  Gear toGear(Element listItem);
}
