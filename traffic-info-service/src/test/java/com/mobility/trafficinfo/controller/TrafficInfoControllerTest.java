package com.mobility.trafficinfo.controller;

import com.mobility.trafficinfo.entity.TrafficInfo;
import com.mobility.trafficinfo.service.TrafficInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TrafficInfoControllerTest {

    @Mock
    private TrafficInfoService trafficInfoService;

    @InjectMocks
    private TrafficInfoController trafficInfoController;

    private TrafficInfo trafficInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        trafficInfo = new TrafficInfo();
        trafficInfo.setId(1L);
        trafficInfo.setZipCode("12345");
        trafficInfo.setCongestionLevel(5);
        trafficInfo.setTimestamp(LocalDateTime.now());
    }

    @Test
    void createTrafficInfo_ShouldReturnCreatedTrafficInfo() {
        when(trafficInfoService.saveTrafficInfo(any(TrafficInfo.class))).thenReturn(trafficInfo);

        ResponseEntity<TrafficInfo> response = trafficInfoController.createTrafficInfo(trafficInfo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(trafficInfo, response.getBody());
        verify(trafficInfoService, times(1)).saveTrafficInfo(any(TrafficInfo.class));
    }

    @Test
    void getTrafficInfoByZipCode_ShouldReturnTrafficInfoList() {
        List<TrafficInfo> trafficInfoList = Arrays.asList(trafficInfo);
        when(trafficInfoService.getTrafficInfoByZipCode("12345")).thenReturn(trafficInfoList);

        ResponseEntity<List<TrafficInfo>> response = trafficInfoController.getTrafficInfoByZipCode("12345");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(trafficInfo, response.getBody().get(0));
        verify(trafficInfoService, times(1)).getTrafficInfoByZipCode("12345");
    }

    @Test
    void getAllTrafficInfo_ShouldReturnAllTrafficInfo() {
        List<TrafficInfo> allTrafficInfo = Arrays.asList(trafficInfo);
        when(trafficInfoService.getAllTrafficInfo()).thenReturn(allTrafficInfo);

        ResponseEntity<List<TrafficInfo>> response = trafficInfoController.getAllTrafficInfo();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(trafficInfo, response.getBody().get(0));
        verify(trafficInfoService, times(1)).getAllTrafficInfo();
    }
}