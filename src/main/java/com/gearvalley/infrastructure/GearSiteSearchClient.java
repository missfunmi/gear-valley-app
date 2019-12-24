package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.SearchResult;
import java.util.List;

public interface GearSiteSearchClient {

  List<SearchResult> searchForGearByKeyword(String keyword);

}
