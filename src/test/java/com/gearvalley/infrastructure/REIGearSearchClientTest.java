package com.gearvalley.infrastructure;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.gearvalley.domain.PriceWatchService;
import com.gearvalley.domain.models.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
public class REIGearSearchClientTest {

  @Autowired private JsoupClient jsoupClient;
  @Autowired private PriceWatchService priceWatchService;
  private REIGearSearchClient reiGearSearchClient;

  @BeforeEach
  public void setUp() {
    assertTrue(jsoupClient instanceof MockJsoupClient);
    reiGearSearchClient = new REIGearSearchClient(jsoupClient, priceWatchService);
  }

  @Test
  public void testSearchWithRealText() {
    SearchResult searchResult = reiGearSearchClient.searchForGearByKeyword("marmot precip jacket");
    assertThat(searchResult, notNullValue());
    // TODO more assertions
  }

  @Test
  public void testSearchWithGibberish() {
    reiGearSearchClient.searchForGearByKeyword("saehtjjfkpalqdnopspdslnakpejfends");
    // TODO real test
  }
}
