package com.gearvalley.infrastructure;

import com.gearvalley.domain.GearSiteSearchFacade;
import com.gearvalley.domain.models.SearchRequest;
import com.gearvalley.domain.models.SearchResponse;
import com.gearvalley.domain.models.SearchResult;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultGearSiteSearchFacade implements GearSiteSearchFacade {

  // TODO - Just one implementation for now
  private GearSiteSearchClient gearSiteSearchClient;

  @Autowired
  public DefaultGearSiteSearchFacade(GearSiteSearchClient gearSiteSearchClient) {
    this.gearSiteSearchClient = gearSiteSearchClient;
  }

  @Override
  public SearchResponse searchGearSites(SearchRequest searchRequest, String... sites) {
    // TODO -- Execute lines 19-20 for all sites in specified request
    SearchResult searchResult =
        gearSiteSearchClient.searchForGearByKeyword(searchRequest.getKeyword());

    SearchResponse searchResponse =
        SearchResponse.builder()
            .keyword(searchRequest.getKeyword())
            .results(Lists.newArrayList(searchResult)) // TODO -- collect all results from all sites
            .build();

    return searchResponse;
  }
}
