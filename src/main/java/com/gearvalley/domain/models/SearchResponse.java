package com.gearvalley.domain.models;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResponse {
  private String keyword;
  private List<SearchResult> results;
  // TODO add timestamp of search response

}
