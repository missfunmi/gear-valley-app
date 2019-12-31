package com.gearvalley.domain.models;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceCheckResult {
  private BigDecimal price;
}
