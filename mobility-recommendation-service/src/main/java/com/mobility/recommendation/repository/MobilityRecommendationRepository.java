package com.mobility.recommendation.repository;

import com.mobility.recommendation.entity.MobilityRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobilityRecommendationRepository extends JpaRepository<MobilityRecommendation, Long> {
    MobilityRecommendation findTopByZipCodeOrderByTimestampDesc(String zipCode);
}
