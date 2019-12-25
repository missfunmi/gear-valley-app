package com.gearvalley.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearvalley.domain.models.SearchRequest;
import com.gearvalley.domain.models.SearchResponse;
import com.google.common.io.Resources;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
@Slf4j
public class MockSearchService implements SearchService {
  private final ObjectMapper objectMapper;
  private final SearchResponse mockSearchResponse;
  private static final String SEARCH_RESPONSE_FILE = "mocks/search-results.json";

  @Autowired
  public MockSearchService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;

    try {
      URL resourcePath = Resources.getResource(SEARCH_RESPONSE_FILE);
      this.mockSearchResponse = this.objectMapper.readValue(resourcePath, SearchResponse.class);
    } catch (Exception e) {
      throw new RuntimeException("Oops. Error loading mock JSON file:", e);
    }
  }

  @Override
  public SearchResponse search(SearchRequest searchRequest) {
    return mockSearchResponse;
  }
}
