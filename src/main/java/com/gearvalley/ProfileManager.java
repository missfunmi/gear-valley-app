package com.gearvalley;

import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProfileManager {

  @Autowired private Environment environment;

  /**
   * TODO
   * Temporary sanity check to prevent accidentally hitting real websites during local dev.
   * Will configure pipeline (or possibly master deploy?) to have active profiles = prod
   */
  @Value("${application.expected.active.profile}")
  private String expectedActiveProfile;

  @PostConstruct
  public void checkActiveProfiles() {
    for (String profileName : environment.getActiveProfiles()) {
      log.info("Currently active profile={}", profileName);
      if (!Objects.equals(profileName, expectedActiveProfile)) {
        throw new UnsupportedOperationException(
            "Running a profile=" + profileName + " that is not expectedActiveProfile="
                + expectedActiveProfile + "! Is this intentional? Application will shut down.");
      }
    }
  }
}
