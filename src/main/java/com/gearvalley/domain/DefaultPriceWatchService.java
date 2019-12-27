package com.gearvalley.domain;

import com.gearvalley.domain.models.PriceWatch;
import com.gearvalley.domain.models.PriceWatchAddRequest;
import com.gearvalley.infrastructure.PriceWatchRepository;
import com.mongodb.MongoException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class DefaultPriceWatchService implements PriceWatchService {

  private final PriceWatchRepository priceWatchRepository;
  private final Random random;
  private static final Integer MAX_WATCHES = 0X1000000;

  @Autowired
  public DefaultPriceWatchService(PriceWatchRepository priceWatchRepository) {
    this.priceWatchRepository = priceWatchRepository;
    this.random = new Random();
  }

  @Override
  public PriceWatch addWatch(PriceWatchAddRequest priceWatchAddRequest) {
    PriceWatch priceWatch =
        PriceWatch.builder()
            .watchId(randomWatchId())
            .keyword(priceWatchAddRequest.getKeyword())
            .title(priceWatchAddRequest.getGear().getTitle())
            .description(priceWatchAddRequest.getGear().getDescription())
            .providerId(priceWatchAddRequest.getProviderId())
            .lastPriceCheck(Instant.now())
            .currentPrice(priceWatchAddRequest.getGear().getPrice())
            .startingPrice(priceWatchAddRequest.getGear().getPrice())
            .isActive(true)
            .image(priceWatchAddRequest.getGear().getImage())
            .build();
    return priceWatchRepository.save(priceWatch);
  }

  @Override
  public List<PriceWatch> fetchAllWatches() {
    return priceWatchRepository.findAll();
  }

  @Override
  public Optional<PriceWatch> fetchWatchById(String watchId) {
    return priceWatchRepository.findByWatchId(watchId);
  }

  @Override
  public PriceWatch deleteWatch(String watchId) {
    var deletedWatches = priceWatchRepository.deleteByWatchId(watchId);
    if (CollectionUtils.isEmpty(deletedWatches)) {
      throw new IllegalArgumentException(
          "Did not find nonexistent watchId=" + watchId + " to delete");
    }
    if (deletedWatches.size() > 1) {
      throw new MongoException(
          "Unexpectedly found and deleted more than 1 watch for the same watchId! Here are the watches deleted: "
              + deletedWatches);
    }
    return deletedWatches.get(0);
  }

  String randomWatchId() {
    while (true) {
      var newWatchId = String.format("%06x", random.nextInt(MAX_WATCHES) + 1);
      var existingWatch = priceWatchRepository.findByWatchId(newWatchId);
      if (!existingWatch.isPresent()) {
        return newWatchId;
      }
    }
  }
}
