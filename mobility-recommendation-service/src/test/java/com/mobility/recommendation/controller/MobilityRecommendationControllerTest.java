package com.mobility.recommendation.controller;

import com.mobility.recommendation.entity.MobilityRecommendation;
import com.mobility.recommendation.service.MobilityRecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MobilityRecommendationControllerTest {

    @Mock
    private MobilityRecommendationService recommendationService;

    @InjectMocks
    private MobilityRecommendationController recommendationController;

    private MobilityRecommendation recommendation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recommendation = new MobilityRecommendation();
        recommendation.setId(1L);
        recommendation.setZipCode("12345");
        recommendation.setRecommendedMode("Car");
        recommendation.setTimestamp(LocalDateTime.now());
    }

    @Test
    void getRecommendation_ShouldReturnRecommendation() {
        when(recommendationService.getRecommendation("12345")).thenReturn(recommendation);

        ResponseEntity<MobilityRecommendation> response = recommendationController.getRecommendation("12345");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(recommendation, response.getBody());
        verify(recommendationService, times(1)).getRecommendation("12345");
    }
}