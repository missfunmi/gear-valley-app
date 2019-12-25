package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.SearchResult;

public interface GearSiteSearchClient {

  SearchResult searchForGearByKeyword(String keyword);
}
