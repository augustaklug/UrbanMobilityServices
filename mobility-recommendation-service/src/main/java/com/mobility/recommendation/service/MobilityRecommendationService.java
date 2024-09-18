package com.mobility.recommendation.service;

import com.mobility.recommendation.client.TrafficInfoClient;
import com.mobility.recommendation.client.TrafficInfoDTO;
import com.mobility.recommendation.entity.MobilityRecommendation;
import com.mobility.recommendation.exception.ResourceNotFoundException;
import com.mobility.recommendation.repository.MobilityRecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MobilityRecommendationService {

    private final MobilityRecommendationRepository recommendationRepository;
    private final TrafficInfoClient trafficInfoClient;

    public MobilityRecommendation getRecommendation(String zipCode) {
        try {
            log.info("Fetching traffic info for ZIP code: {}", zipCode);
            List<TrafficInfoDTO> trafficInfoList = trafficInfoClient.getTrafficInfo(zipCode);
            log.info("Received traffic info: {}", trafficInfoList);

            if (trafficInfoList.isEmpty()) {
                throw new ResourceNotFoundException("No traffic information found for ZIP code: " + zipCode);
            }

            TrafficInfoDTO latestTrafficInfo = trafficInfoList.get(0); // Assume the first item is the most recent
            String recommendedMode = determineRecommendedMode(latestTrafficInfo.getCongestionLevel());
            log.info("Determined recommended mode: {}", recommendedMode);

            MobilityRecommendation recommendation = new MobilityRecommendation();
            recommendation.setZipCode(zipCode);
            recommendation.setRecommendedMode(recommendedMode);
            recommendation.setTimestamp(LocalDateTime.now());

            MobilityRecommendation savedRecommendation = recommendationRepository.save(recommendation);
            log.info("Saved recommendation: {}", savedRecommendation);

            return savedRecommendation;
        } catch (Exception e) {
            log.error("Error getting recommendation for ZIP code: {}", zipCode, e);
            throw new ResourceNotFoundException("Unable to get recommendation for ZIP code: " + zipCode);
        }
    }

    private String determineRecommendedMode(int congestionLevel) {
        if (congestionLevel <= 3) {
            return "Car";
        } else if (congestionLevel <= 6) {
            return "Public Transport";
        } else {
            return "Bicycle or Walking";
        }
    }
}