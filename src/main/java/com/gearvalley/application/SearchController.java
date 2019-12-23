package com.gearvalley.application;

import com.gearvalley.domain.SearchService;
import com.gearvalley.domain.models.SearchRequest;
import com.gearvalley.domain.models.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
@Slf4j
public class SearchController {

  private final SearchService searchService;

  @Autowired
  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }

  @PostMapping("")
  public ResponseEntity<SearchResponse> search(SearchRequest searchRequest) {
    log.info("Searching with searchRequest={}", searchRequest);
    SearchResponse searchResponse = searchService.search(searchRequest);
    return ResponseEntity.ok(searchResponse);
  }

}
