package com.gearvalley.domain.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Hello {
  private final String value;
}
