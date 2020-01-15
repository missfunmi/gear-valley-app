package com.gearvalley;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterConfig {

  // TODO - will clean this up later
  public @Bean Twitter twitter() {
    twitter4j.conf.Configuration config =
        new ConfigurationBuilder()
            .setOAuthConsumerKey(System.getenv("TWITTER_API_KEY"))
            .setOAuthConsumerSecret(System.getenv("TWITTER_API_SECRET"))
            .setOAuthAccessToken(System.getenv("TWITTER_ACCESS_TOKEN"))
            .setOAuthAccessTokenSecret(System.getenv("TWITTER_ACCESS_TOKEN_SECRET"))
            .build();
    Twitter twitter = new TwitterFactory(config).getInstance();
    return twitter;
  }
}
