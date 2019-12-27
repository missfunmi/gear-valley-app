package com.gearvalley.domain.models;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResult {
  private String providerId; // TODO rename to sourceId?
  private String providerName; // TODO rename to sourceName?
  private String providerHomePage; // TODO rename to searchResultsURL?
  private String providerLogo;
  private List<Gear> gear;
}
