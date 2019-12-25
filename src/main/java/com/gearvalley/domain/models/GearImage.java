package com.gearvalley.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString.Exclude;

@Data
@Builder
public class GearImage {
  @Exclude
  private String base64Image;
  private String contentType;

}
