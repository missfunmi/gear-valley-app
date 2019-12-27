package com.gearvalley.domain.models;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchRequest {
  @NotBlank private String keyword;
  // TODO - add a new field for list of selected sites

}
