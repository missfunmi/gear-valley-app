package com.gearvalley.domain.models;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Gear {
  @NotBlank private String url;
  @NotBlank private String title;
  @NotBlank private String description;
  @NotNull private BigDecimal price;
  private GearImage image;
}
