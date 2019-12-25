package com.gearvalley;

import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProfileManager {

  @Autowired private Environment environment;

  /**
   * Sanity check to ensure we're not hitting real websites while in dev mode Will configure prod
   * pipeline (or possibly master deploy?) to have active profiles = production
   */
  private static final String EXPECTED_ACTIVE_PROFILE = "dev";

  @PostConstruct
  public void checkActiveProfiles() {
    for (String profileName : environment.getActiveProfiles()) {
      log.info("Currently active profile={}", profileName);
      if (!Objects.equals(profileName, EXPECTED_ACTIVE_PROFILE)) {
        throw new RuntimeException(
            "Running a profile=" + profileName + " that is not dev. Is this intentional?");
      }
    }
  }
}
