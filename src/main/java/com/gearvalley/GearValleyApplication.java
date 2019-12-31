package com.gearvalley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GearValleyApplication {

  public static void main(String[] args) {
    SpringApplication.run(GearValleyApplication.class, args);
  }
}
