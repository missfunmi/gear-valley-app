package com.gearvalley.infrastructure;

import org.junit.jupiter.api.Test;

public class REIGearSearchClientTest {

  private REIGearSearchClient reiGearSearchClient = new REIGearSearchClient();

  @Test
  public void testSearchWithRealText() {
    reiGearSearchClient.searchForGearByKeyword("marmot precip jacket");
    
  }

  @Test
  public void testSearchWithGibberish() {
    reiGearSearchClient.searchForGearByKeyword("bfjhgwhjduijdlkaslkfklasjjklfhkas");

  }

}
