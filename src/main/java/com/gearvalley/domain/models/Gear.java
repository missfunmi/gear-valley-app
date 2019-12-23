package com.gearvalley.domain.models;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Gear {
  private String url;
  private String base64Image;
  private String title;
  private String size;
  private BigDecimal price;
}
