package com.gearvalley;

import com.gearvalley.domain.models.PriceWatch;
import com.gearvalley.infrastructure.PriceWatchRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackageClasses = PriceWatchRepository.class)
@Slf4j
public class MongoConfig {

  @Value("${application.database.uri}")
  private String uri;

  @Value("${application.database.name}")
  private String database;

  @Primary
  public @Bean MongoClient mongoClient() {
    // TODO - will clean this up later
    var productionDbUri = System.getenv("MONGODB_URI");
    if (StringUtils.isNotBlank(productionDbUri)) {
      log.info("Resolved 'MONGODB_URI' environment variable, will use this uri instead");
      uri = productionDbUri;
    }
    log.info("Initializing MongoClient with connectionString={}", uri);
    return MongoClients.create(uri);
  }

  @Primary
  public @Bean MongoTemplate mongoTemplate() {
    log.info("Initializing MongoTemplate with database={}", database);
    var mongoTemplate = new MongoTemplate(mongoClient(), database);
    mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
    return mongoTemplate;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void initIndicesAfterStartup() {
    log.info("Initializing indices post application startup...");
    var start = System.currentTimeMillis();

    IndexOperations indexOperations = mongoTemplate().indexOps(PriceWatch.class);
    indexOperations.ensureIndex(new Index("watchId", Direction.ASC).unique());
    indexOperations.ensureIndex(new Index("url", Direction.ASC));
    log.debug(
        "Collection=PriceWatch in database={} has indices={}",
        database,
        indexOperations.getIndexInfo());

    log.info("Completed index resolution in {}ms", (System.currentTimeMillis() - start));
  }
}
