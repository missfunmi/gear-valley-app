package com.gearvalley.infrastructure;

import com.gearvalley.domain.GearSiteSearchFacade;
import com.gearvalley.domain.models.SearchRequest;
import com.gearvalley.domain.models.SearchResponse;

public class DefaultGearSiteSearchFacade implements GearSiteSearchFacade {

  @Override
  public SearchResponse searchAllSites(SearchRequest searchRequest) {
    // Iterate through all site clients 
    return null;
  }
}
