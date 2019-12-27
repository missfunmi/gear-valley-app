package com.gearvalley.domain.models;

import java.time.Instant;
import java.util.List;
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
  private PriceDetail currentPrice;
  private List<PriceDetail> priceHistory;
  private boolean isActive;
  @Exclude
  private GearImage image;
}
