package com.gearvalley;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class GearValleyApplication {

  public static void main(String[] args) {
    SpringApplication.run(GearValleyApplication.class, args);
  }

  @Configuration
  public class WebConfig implements WebMvcConfigurer {
    public static final String NOT_FOUND_PATH = "/notFound";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
      registry
          .addViewController(NOT_FOUND_PATH)
          .setStatusCode(HttpStatus.OK)
          .setViewName("forward:/index.html");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
      return container ->
          container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, NOT_FOUND_PATH));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry
          .addResourceHandler("/index.html")
          .addResourceLocations("classpath:public/index.html")
          .setCacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS).mustRevalidate())
          .setCacheControl(CacheControl.noCache())
          .setCacheControl(CacheControl.noStore())
          .resourceChain(true);

      registry
          .addResourceHandler("/static/**", "/favicon.ico")
          .addResourceLocations("classpath:public/static/", "classpath:public/favicon.ico")
          .setCachePeriod(Long.valueOf(Duration.ofDays(365).getSeconds()).intValue())
          .resourceChain(true);
    }
  }
}
