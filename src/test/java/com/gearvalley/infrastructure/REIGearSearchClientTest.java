package com.gearvalley.infrastructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
public class REIGearSearchClientTest {

  @Autowired private JsoupClient jsoupClient;
  private REIGearSearchClient reiGearSearchClient;

  @BeforeEach
  public void setUp() {
    assertTrue(jsoupClient instanceof MockJsoupClient);
    reiGearSearchClient = new REIGearSearchClient(jsoupClient);
  }

  @Test
  public void testSearchWithRealText() {
    reiGearSearchClient.searchForGearByKeyword("marmot precip jacket");
  }

  @Test
  public void testSearchWithGibberish() {
    reiGearSearchClient.searchForGearByKeyword("saehtjjfkpalqdnopspdslnakpejfends");
  }
}
