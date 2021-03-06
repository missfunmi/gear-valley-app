package com.gearvalley.application;

import com.gearvalley.domain.SearchService;
import com.gearvalley.domain.models.SearchRequest;
import com.gearvalley.domain.models.SearchResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping
  public ResponseEntity<SearchResponse> search(
      @RequestBody @Valid @NotNull SearchRequest searchRequest) {
    log.info("Searching with searchRequest={}", searchRequest);
    var searchResponse = searchService.search(searchRequest);
    log.info("Returning searchResponse={}", searchResponse);
    return ResponseEntity.ok(searchResponse);
  }
}
