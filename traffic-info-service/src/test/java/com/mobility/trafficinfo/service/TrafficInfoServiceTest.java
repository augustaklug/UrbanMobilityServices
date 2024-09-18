package com.mobility.trafficinfo.service;

import com.mobility.trafficinfo.exception.ResourceNotFoundException;
import com.mobility.trafficinfo.entity.TrafficInfo;
import com.mobility.trafficinfo.repository.TrafficInfoRepository;
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
class TrafficInfoServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Mock
    private TrafficInfoRepository trafficInfoRepository;

    @InjectMocks
    private TrafficInfoService trafficInfoService;

    private TrafficInfo trafficInfo;

    @BeforeEach
    void setUp() {
        trafficInfo = new TrafficInfo();
        trafficInfo.setId(1L);
        trafficInfo.setZipCode("12345");
        trafficInfo.setCongestionLevel(5);
        trafficInfo.setTimestamp(LocalDateTime.now());
    }

    @Test
    void saveTrafficInfo_ShouldSaveAndReturnTrafficInfo() {
        when(trafficInfoRepository.save(any(TrafficInfo.class))).thenReturn(trafficInfo);

        TrafficInfo savedTrafficInfo = trafficInfoService.saveTrafficInfo(trafficInfo);

        assertNotNull(savedTrafficInfo);
        assertEquals(trafficInfo.getZipCode(), savedTrafficInfo.getZipCode());
        assertEquals(trafficInfo.getCongestionLevel(), savedTrafficInfo.getCongestionLevel());
        verify(trafficInfoRepository, times(1)).save(any(TrafficInfo.class));
    }

    @Test
    void getTrafficInfoByZipCode_ShouldReturnListOfTrafficInfo() {
        List<TrafficInfo> trafficInfoList = Arrays.asList(trafficInfo);
        when(trafficInfoRepository.findByZipCodeOrderByTimestampDesc("12345")).thenReturn(trafficInfoList);

        List<TrafficInfo> result = trafficInfoService.getTrafficInfoByZipCode("12345");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(trafficInfo.getZipCode(), result.get(0).getZipCode());
        verify(trafficInfoRepository, times(1)).findByZipCodeOrderByTimestampDesc("12345");
    }

    @Test
    void getTrafficInfoByZipCode_ShouldThrowResourceNotFoundException() {
        when(trafficInfoRepository.findByZipCodeOrderByTimestampDesc("12345")).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> trafficInfoService.getTrafficInfoByZipCode("12345"));
        verify(trafficInfoRepository, times(1)).findByZipCodeOrderByTimestampDesc("12345");
    }

    @Test
    void getAllTrafficInfo_ShouldReturnListOfAllTrafficInfo() {
        List<TrafficInfo> trafficInfoList = Arrays.asList(trafficInfo);
        when(trafficInfoRepository.findAll()).thenReturn(trafficInfoList);

        List<TrafficInfo> result = trafficInfoService.getAllTrafficInfo();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(trafficInfoRepository, times(1)).findAll();
    }

    @Test
    void getAllTrafficInfo_ShouldThrowResourceNotFoundException() {
        when(trafficInfoRepository.findAll()).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> trafficInfoService.getAllTrafficInfo());
        verify(trafficInfoRepository, times(1)).findAll();
    }
}
