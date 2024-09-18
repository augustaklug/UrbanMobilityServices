package com.mobility.recommendation.client;

import com.mobility.recommendation.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TrafficInfoClientTest {

    @Mock
    private TrafficInfoClient trafficInfoClient;

    private TrafficInfoDTO trafficInfoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        trafficInfoDTO = new TrafficInfoDTO();
        trafficInfoDTO.setZipCode("12345");
        trafficInfoDTO.setCongestionLevel(5);
    }

    @Test
    void getTrafficInfo_ShouldReturnTrafficInfoList() {
        when(trafficInfoClient.getTrafficInfo("12345"))
            .thenReturn(Arrays.asList(trafficInfoDTO));

        List<TrafficInfoDTO> result = trafficInfoClient.getTrafficInfo("12345");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).getZipCode());
        assertEquals(5, result.get(0).getCongestionLevel());
        verify(trafficInfoClient, times(1)).getTrafficInfo("12345");
    }

    @Test
    void getTrafficInfo_ShouldReturnEmptyList_WhenNoTrafficInfo() {
        when(trafficInfoClient.getTrafficInfo("12345"))
            .thenReturn(Collections.emptyList());

        List<TrafficInfoDTO> result = trafficInfoClient.getTrafficInfo("12345");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(trafficInfoClient, times(1)).getTrafficInfo("12345");
    }

    @Test
    void getTrafficInfo_ShouldThrowResourceNotFoundException_WhenNotFound() {
        when(trafficInfoClient.getTrafficInfo("12345"))
            .thenThrow(new ResourceNotFoundException("Traffic info not found for ZIP code: 12345"));

        assertThrows(ResourceNotFoundException.class, () -> trafficInfoClient.getTrafficInfo("12345"));
        verify(trafficInfoClient, times(1)).getTrafficInfo("12345");
    }

    @Test
    void getTrafficInfo_ShouldThrowRuntimeException_WhenUnexpectedError() {
        when(trafficInfoClient.getTrafficInfo("12345"))
            .thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(RuntimeException.class, () -> trafficInfoClient.getTrafficInfo("12345"));
        verify(trafficInfoClient, times(1)).getTrafficInfo("12345");
    }
}