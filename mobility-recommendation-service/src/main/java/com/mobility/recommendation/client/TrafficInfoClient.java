package com.mobility.recommendation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "traffic-info-service", url = "${traffic-info-service.url}")
public interface TrafficInfoClient {

    @GetMapping("/api/traffic/{zipCode}")
    List<TrafficInfoDTO> getTrafficInfo(@PathVariable("zipCode") String zipCode);
}
