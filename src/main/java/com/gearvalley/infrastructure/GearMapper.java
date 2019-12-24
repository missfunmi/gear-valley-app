package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.Gear;
import org.jsoup.nodes.Element;

//@Mapper
public interface GearMapper {

  /**
   *

  @Mappings({
      @Mapping(target="", source="")
  })
      

      url = li > a.href --> returns relative path
      base64Image = li > a > div > img.src --> returns relative path to JPEG image
      brand =
      description =
        title = brand + space + description
      metadata = li > a > 
      price;

   Other useful fields
      li > div > div > svg (data-ui="product-badge-garage") --> returns REI OUTLET logo
   */
  Gear toGear(Element listItem);

}
