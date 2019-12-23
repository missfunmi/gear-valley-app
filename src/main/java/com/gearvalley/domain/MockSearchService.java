package com.gearvalley.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearvalley.domain.models.SearchRequest;
import com.gearvalley.domain.models.SearchResponse;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultSearchService implements SearchService {

  private final ObjectMapper objectMapper;
  private final SearchResponse mockSearchResponse;
  private static final String SEARCH_RESPONSE_FILE = "mocks/search-results.json";

  @Autowired
  public DefaultSearchService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;

    try {
      Resource resource = new ClassPathResource(SEARCH_RESPONSE_FILE);
      File file = resource.getFile();
      this.mockSearchResponse = this.objectMapper.readValue(file, SearchResponse.class);
    } catch (Exception e) {
      throw new RuntimeException("Oops. Error loading mock JSON file:", e);
    }
  }

  @Override
  public SearchResponse search(SearchRequest searchRequest) {
    return mockSearchResponse;
  }
}
