package com.gearvalley.domain.models;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Gear {
  private String url;
  private GearImage image;
  private String title;
  private String description;
  private BigDecimal price;
}
