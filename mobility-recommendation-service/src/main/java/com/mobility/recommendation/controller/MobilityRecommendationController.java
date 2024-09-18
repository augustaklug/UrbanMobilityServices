package com.mobility.recommendation.controller;

import com.mobility.recommendation.entity.MobilityRecommendation;
import com.mobility.recommendation.service.MobilityRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class MobilityRecommendationController {

    private final MobilityRecommendationService recommendationService;

    @GetMapping("/{zipCode}")
    public ResponseEntity<MobilityRecommendation> getRecommendation(@PathVariable String zipCode) {
        MobilityRecommendation recommendation = recommendationService.getRecommendation(zipCode);
        return ResponseEntity.ok(recommendation);
    }
}
