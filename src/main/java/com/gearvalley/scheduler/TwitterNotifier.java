package com.gearvalley.scheduler;

import com.gearvalley.domain.models.PriceWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
@Slf4j
public class TwitterNotifier implements Notifier {
  @Autowired private Twitter twitter;

  private static final String PRICE_WATCH_UPDATED_TWEET =
      "Price watch updated for %s, new price = $%.2f, old price = $%.2f";

  @Override
  public void notifyUpdatedPriceWatch(PriceWatch priceWatch) {
    String tweet =
        String.format(
            PRICE_WATCH_UPDATED_TWEET,
            priceWatch.getTitle(),
            priceWatch.getCurrentPrice().getPrice(),
            priceWatch.getPriceHistory().get(0).getPrice());

    try {
      Status status = twitter.updateStatus(tweet);
      log.info("Successfully notified tweet='{}' for priceWatch={}", tweet, priceWatch);
    } catch (TwitterException e) {
      throw new RuntimeException("An error occurred while notifying of price watch change", e);
    }
  }
}
