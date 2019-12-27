package com.gearvalley.domain.models;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.ToString.Exclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class PriceWatch {
  @Id private String id;
  @CreatedDate private Instant created;
  @LastModifiedDate private Instant lastModified;

  @Indexed(unique = true)
  private String watchId;

  private String keyword;
  private String title;
  private String description;
  private String providerId;
  private String url;
  private Instant lastPriceCheck;
  private BigDecimal currentPrice;
  private BigDecimal startingPrice;
  private boolean isActive;
  @Exclude
  private GearImage image;
}
