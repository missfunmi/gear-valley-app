package com.gearvalley;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GearValleyApplication {

  public static void main(String[] args) {
    SpringApplication.run(GearValleyApplication.class, args);
  }

  @Configuration
  public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/index.html")
          .addResourceLocations("classpath:public/index.html")
          .setCacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS)
              .mustRevalidate())
          .setCacheControl(CacheControl.noCache())
          .setCacheControl(CacheControl.noStore())
          .resourceChain(true);

      registry.addResourceHandler("/static/**", "/favicon.ico")
          .addResourceLocations("classpath:public/static/", "classpath:public/favicon.ico")
          .setCachePeriod(Long.valueOf(Duration.ofDays(365).getSeconds()).intValue())
          .resourceChain(true);
    }

  }
}
