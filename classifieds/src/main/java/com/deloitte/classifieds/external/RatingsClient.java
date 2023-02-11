package com.deloitte.classifieds.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient("ratings")
public interface RatingsClient {
    @GetMapping("/ratings/{sellerId}")
    Map<String, String> getAggregateRatingBySellerId(@PathVariable String sellerId);
}
