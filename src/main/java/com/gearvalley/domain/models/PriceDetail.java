package com.gearvalley.domain.models;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDetail {
  private BigDecimal price;
  private Instant dateOfCheck;

}
