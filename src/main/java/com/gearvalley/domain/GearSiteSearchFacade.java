package com.gearvalley.domain;

import com.gearvalley.domain.models.SearchRequest;
import com.gearvalley.domain.models.SearchResponse;

public interface GearSiteSearchFacade {

  SearchResponse searchAllSites(SearchRequest searchRequest);

}
