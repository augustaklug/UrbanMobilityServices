package com.mobility.trafficinfo.service;

import com.mobility.trafficinfo.exception.ResourceNotFoundException;
import com.mobility.trafficinfo.entity.TrafficInfo;
import com.mobility.trafficinfo.repository.TrafficInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficInfoService {

    private final TrafficInfoRepository trafficInfoRepository;

    public TrafficInfo saveTrafficInfo(TrafficInfo trafficInfo) {
        trafficInfo.setTimestamp(LocalDateTime.now());
        return trafficInfoRepository.save(trafficInfo);
    }

    public List<TrafficInfo> getTrafficInfoByZipCode(String zipCode) {
        List<TrafficInfo> trafficInfoList = trafficInfoRepository.findByZipCodeOrderByTimestampDesc(zipCode);
        if (trafficInfoList.isEmpty()) {
            throw new ResourceNotFoundException("No traffic information found for ZIP code: " + zipCode);
        }
        return trafficInfoList;
    }

    public List<TrafficInfo> getAllTrafficInfo() {
        List<TrafficInfo> allTrafficInfo = trafficInfoRepository.findAll();
        if (allTrafficInfo.isEmpty()) {
            throw new ResourceNotFoundException("No traffic information found");
        }
        return allTrafficInfo;
    }
}