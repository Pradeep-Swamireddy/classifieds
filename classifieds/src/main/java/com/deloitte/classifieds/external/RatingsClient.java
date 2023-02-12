package com.deloitte.classifieds.external;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "ratings")
public interface RatingsClient {
    @GetMapping("/ratings/{sellerId}")
    @CircuitBreaker(name = "rating", fallbackMethod = "ratingFallback")
    Map<String, String> getAggregateRatingBySellerId(@PathVariable String sellerId);

    default Map<String, String> ratingFallback(Exception e) {
        return Map.of("", "N/A");
    }
}
