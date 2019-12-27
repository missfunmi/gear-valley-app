package com.gearvalley.domain.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceWatchAddRequest {
  @NotBlank private String keyword;
  @NotBlank private String providerId;
  @NotNull private Gear gear;
}
