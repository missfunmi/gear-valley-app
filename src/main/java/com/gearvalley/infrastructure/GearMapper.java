package com.gearvalley.infrastructure;

import com.gearvalley.domain.models.SearchResult;
import org.jsoup.nodes.Element;

//@Mapper
public interface SearchResultMapper {

  /**
   *

  @Mappings({
      @Mapping(target="", source="")
  })
   */
  SearchResult toSearchResult(Element listItem);

}
