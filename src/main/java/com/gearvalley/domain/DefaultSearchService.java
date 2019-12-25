package com.gearvalley.domain;

import com.gearvalley.domain.models.SearchRequest;
import com.gearvalley.domain.models.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("production")
@Slf4j
public class DefaultSearchService implements SearchService {
  private final GearSiteSearchFacade gearSiteSearchFacade;

  @Autowired
  public DefaultSearchService(GearSiteSearchFacade gearSiteSearchFacade) {
    this.gearSiteSearchFacade = gearSiteSearchFacade;
  }

  @Override
  public SearchResponse search(SearchRequest searchRequest) {
    SearchResponse response = gearSiteSearchFacade.searchGearSites(searchRequest);
    return response;
  }
}
