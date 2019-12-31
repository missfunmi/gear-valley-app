package com.gearvalley.domain.models;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class PriceWatchUpdateRequest {
  @NotBlank private String watchId;
  @Getter(AccessLevel.NONE)
  private Boolean keepActive;
  private BigDecimal newPrice;

  public Boolean shouldKeepActive() {
    return keepActive;
  }
}
