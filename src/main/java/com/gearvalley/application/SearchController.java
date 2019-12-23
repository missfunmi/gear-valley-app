package com.gearvalley.application;

import com.gearvalley.domain.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

  private final SearchService searchService;

  @Autowired
  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }

  @PostMapping("")
  public ResponseEntity search() {
    return ResponseEntity.noContent().build();
  }

}
