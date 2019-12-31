package com.gearvalley.domain;

import com.gearvalley.domain.models.PriceDetail;
import com.gearvalley.domain.models.PriceWatch;
import com.gearvalley.domain.models.PriceWatchAddRequest;
import com.gearvalley.domain.models.PriceWatchUpdateRequest;
import com.gearvalley.infrastructure.PriceWatchRepository;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.NonNull;
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
  public PriceWatch addWatch(@NonNull PriceWatchAddRequest priceWatchAddRequest) {
    PriceDetail currentPrice =
        PriceDetail.builder()
            .price(priceWatchAddRequest.getGear().getPrice())
            .dateOfCheck(Instant.now())
            .build();
    PriceWatch priceWatch =
        PriceWatch.builder()
            .watchId(randomWatchId())
            .keyword(priceWatchAddRequest.getKeyword())
            .title(priceWatchAddRequest.getGear().getTitle())
            .description(priceWatchAddRequest.getGear().getDescription())
            .providerId(priceWatchAddRequest.getProviderId())
            .url(priceWatchAddRequest.getGear().getUrl())
            .currentPrice(currentPrice)
            .priceHistory(Lists.newArrayList(currentPrice))
            .active(true)
            .image(priceWatchAddRequest.getGear().getImage())
            .build();
    return priceWatchRepository.save(priceWatch);
  }

  @Override
  public Optional<PriceWatch> updatePriceWatch(
      @NonNull PriceWatchUpdateRequest priceWatchUpdateRequest) {
    BigDecimal newPrice = priceWatchUpdateRequest.getNewPrice();
    Boolean shouldKeepActive = priceWatchUpdateRequest.shouldKeepActive();

    if (newPrice == null && shouldKeepActive == null) {
      log.warn(
          "Invalid attempt to update price watch using priceWatchUpdateRequest={}",
          priceWatchUpdateRequest);
      return Optional.empty();
    }

    var existingWatch = priceWatchRepository.findByWatchId(priceWatchUpdateRequest.getWatchId());
    if (!existingWatch.isPresent()) {
      return Optional.empty();
    }

    PriceDetail newPriceDetail =
        PriceDetail.builder().price(newPrice).dateOfCheck(Instant.now()).build();

    PriceWatch watchToUpdate = existingWatch.get();
    watchToUpdate.setCurrentPrice(newPriceDetail);
    watchToUpdate.getPriceHistory().add(newPriceDetail);
    watchToUpdate.setActive(priceWatchUpdateRequest.shouldKeepActive());

    return Optional.of(priceWatchRepository.save(watchToUpdate));
  }

  @Override
  public List<PriceWatch> fetchAllWatches() {
    return priceWatchRepository.findAll();
  }

  @Override
  public List<PriceWatch> fetchAllActiveWatches() {
    return priceWatchRepository.findAllActive();
  }

  @Override
  public List<PriceWatch> fetchWatchesByProviderIdAndUrl(String providerId, String url) {
    return priceWatchRepository.findByProviderIdAndUrl(providerId, url);
  }

  @Override
  public Optional<PriceWatch> fetchWatchById(String watchId) {
    return priceWatchRepository.findByWatchId(watchId);
  }

  @Override
  public Optional<PriceWatch> activateWatch(String watchId) {
    var existingWatch = priceWatchRepository.findByWatchId(watchId);
    if (!existingWatch.isPresent()) {
      return Optional.empty();
    }
    PriceWatch watchToActivate = existingWatch.get().activate();
    return Optional.of(priceWatchRepository.save(watchToActivate));
  }

  @Override
  public Optional<PriceWatch> deactivateWatch(String watchId) {
    var existingWatch = priceWatchRepository.findByWatchId(watchId);
    if (!existingWatch.isPresent()) {
      return Optional.empty();
    }
    PriceWatch watchToDeactivate = existingWatch.get().deactivate();
    return Optional.of(priceWatchRepository.save(watchToDeactivate));
  }

  @Override
  public Optional<PriceWatch> deleteWatch(String watchId) {
    var deletedWatches = priceWatchRepository.deleteByWatchId(watchId);
    if (CollectionUtils.isEmpty(deletedWatches)) {
      return Optional.empty();
    }
    if (deletedWatches.size() > 1) {
      log.error(
          "Unexpectedly found and deleted more than 1 watch for the same watchId! deletedWatches={}",
          deletedWatches);
    }
    return Optional.of(deletedWatches.get(0));
  }

  String randomWatchId() {
    while (true) {
      var newWatchId =
          String.format(
              "%06x",
              random.nextInt(MAX_WATCHES) + 1); // +1 ensures 000000 is never returned as a watchId
      var existingWatch = priceWatchRepository.findByWatchId(newWatchId);
      if (!existingWatch.isPresent()) {
        return newWatchId;
      }
    }
  }
}
