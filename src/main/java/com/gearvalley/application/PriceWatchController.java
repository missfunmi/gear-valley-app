package com.gearvalley.application;

import com.gearvalley.domain.PriceWatchService;
import com.gearvalley.domain.models.PriceWatch;
import com.gearvalley.domain.models.PriceWatchAddRequest;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/priceWatches")
@Slf4j
public class PriceWatchController {
  private final PriceWatchService priceWatchService;

  @Autowired
  public PriceWatchController(PriceWatchService priceWatchService) {
    this.priceWatchService = priceWatchService;
  }

  @PostMapping
  public ResponseEntity<PriceWatch> addPriceWatch(
      @RequestBody @NotNull @Valid PriceWatchAddRequest priceWatchAddRequest) {
    log.info("Adding new priceWatchAddRequest={}", priceWatchAddRequest);
    var addedWatch = priceWatchService.addWatch(priceWatchAddRequest);
    log.info("Returning successfully created priceWatch={}", addedWatch);
    return ResponseEntity.ok(addedWatch);
  }

  @GetMapping
  public ResponseEntity<Envelope<List<PriceWatch>>> fetchAllPriceWatches() {
    var allWatches = priceWatchService.fetchAllWatches();
    log.info("Returning all existing priceWatches={}", allWatches);
    return ResponseEntity.ok(new Envelope<>(allWatches));
  }

  @GetMapping("/{watchId}")
  public ResponseEntity<PriceWatch> fetchSinglePriceWatch(@PathVariable @NotBlank String watchId) {
    return priceWatchService
        .fetchWatchById(watchId)
        .map(
            (priceWatch) -> {
              log.info("Found requested priceWatch={}", priceWatch);
              return ResponseEntity.ok(priceWatch);
            })
        .orElseGet(
            () -> {
              log.info("No matching priceWatch found for watchId={}", watchId);
              return ResponseEntity.notFound().build();
            });
  }

  @DeleteMapping("/{watchId}")
  public ResponseEntity<PriceWatch> deletePriceWatch(@PathVariable @NotBlank String watchId) {
    var deletedWatch = priceWatchService.deleteWatch(watchId);
    log.info("Deleted priceWatch={}", deletedWatch);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{watchId}/disable")
  public ResponseEntity<PriceWatch> disablePriceWatch(@PathVariable @NotBlank String watchId) {
    throw new NotImplementedException("disablePriceWatch command is not yet implemented");
  }
}
