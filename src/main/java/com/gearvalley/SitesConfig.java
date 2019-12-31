package com.gearvalley;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "application.sites")
public class SitesConfig {

  // TODO Refactor
  private Site rei;

  @Data
  public static class Site {
    private String providerId;
    private String baseSite;
  }
}
