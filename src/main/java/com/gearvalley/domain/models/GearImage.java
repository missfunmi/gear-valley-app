package com.gearvalley.domain.models;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString.Exclude;

@Data
@Builder
public class GearImage {
  @NotBlank private String contentType;
  @Exclude private String base64Image;
}
