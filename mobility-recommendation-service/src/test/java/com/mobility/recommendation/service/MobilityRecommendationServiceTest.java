package com.mobility.recommendation.service;

import com.mobility.recommendation.client.TrafficInfoClient;
import com.mobility.recommendation.client.TrafficInfoDTO;
import com.mobility.recommendation.entity.MobilityRecommendation;
import com.mobility.recommendation.exception.ResourceNotFoundException;
import com.mobility.recommendation.repository.MobilityRecommendationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Testcontainers
class MobilityRecommendationServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Mock
    private MobilityRecommendationRepository recommendationRepository;

    @Mock
    private TrafficInfoClient trafficInfoClient;

    @InjectMocks
    private MobilityRecommendationService recommendationService;

    private TrafficInfoDTO trafficInfoDTO;
    private MobilityRecommendation mobilityRecommendation;

    @BeforeEach
    void setUp() {
        trafficInfoDTO = new TrafficInfoDTO();
        trafficInfoDTO.setZipCode("12345");
        trafficInfoDTO.setCongestionLevel(5);

        mobilityRecommendation = new MobilityRecommendation();
        mobilityRecommendation.setId(1L);
        mobilityRecommendation.setZipCode("12345");
        mobilityRecommendation.setRecommendedMode("Public Transport");
        mobilityRecommendation.setTimestamp(LocalDateTime.now());
    }

    @Test
    void getRecommendation_ShouldReturnRecommendation() {
        when(trafficInfoClient.getTrafficInfo("12345")).thenReturn(Arrays.asList(trafficInfoDTO));
        when(recommendationRepository.save(any(MobilityRecommendation.class))).thenReturn(mobilityRecommendation);

        MobilityRecommendation result = recommendationService.getRecommendation("12345");

        assertNotNull(result);
        assertEquals("12345", result.getZipCode());
        assertEquals("Public Transport", result.getRecommendedMode());
        verify(trafficInfoClient, times(1)).getTrafficInfo("12345");
        verify(recommendationRepository, times(1)).save(any(MobilityRecommendation.class));
    }

    @Test
    void getRecommendation_ShouldThrowResourceNotFoundException_WhenNoTrafficInfo() {
        when(trafficInfoClient.getTrafficInfo("12345")).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> recommendationService.getRecommendation("12345"));
        verify(trafficInfoClient, times(1)).getTrafficInfo("12345");
        verify(recommendationRepository, never()).save(any(MobilityRecommendation.class));
    }

    @Test
    void getRecommendation_ShouldRecommendCar_WhenCongestionLevelIsLow() {
        trafficInfoDTO.setCongestionLevel(2);
        when(trafficInfoClient.getTrafficInfo("12345")).thenReturn(Arrays.asList(trafficInfoDTO));

        MobilityRecommendation expectedRecommendation = new MobilityRecommendation();
        expectedRecommendation.setZipCode("12345");
        expectedRecommendation.setRecommendedMode("Car");
        expectedRecommendation.setTimestamp(LocalDateTime.now());

        when(recommendationRepository.save(any(MobilityRecommendation.class))).thenReturn(expectedRecommendation);

        MobilityRecommendation result = recommendationService.getRecommendation("12345");

        assertNotNull(result);
        assertEquals("Car", result.getRecommendedMode());
    }

    @Test
    void getRecommendation_ShouldRecommendBicycleOrWalking_WhenCongestionLevelIsHigh() {
        trafficInfoDTO.setCongestionLevel(8);
        when(trafficInfoClient.getTrafficInfo("12345")).thenReturn(Arrays.asList(trafficInfoDTO));

        MobilityRecommendation expectedRecommendation = new MobilityRecommendation();
        expectedRecommendation.setZipCode("12345");
        expectedRecommendation.setRecommendedMode("Bicycle or Walking");
        expectedRecommendation.setTimestamp(LocalDateTime.now());

        when(recommendationRepository.save(any(MobilityRecommendation.class))).thenReturn(expectedRecommendation);

        MobilityRecommendation result = recommendationService.getRecommendation("12345");

        assertNotNull(result);
        assertEquals("Bicycle or Walking", result.getRecommendedMode());
    }
}