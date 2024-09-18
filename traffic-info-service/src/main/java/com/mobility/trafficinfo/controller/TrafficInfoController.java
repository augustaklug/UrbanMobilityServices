package com.mobility.trafficinfo.controller;

import com.mobility.trafficinfo.entity.TrafficInfo;
import com.mobility.trafficinfo.service.TrafficInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traffic")
@RequiredArgsConstructor
public class TrafficInfoController {

    private final TrafficInfoService trafficInfoService;

    @PostMapping
    public ResponseEntity<TrafficInfo> createTrafficInfo(@RequestBody TrafficInfo trafficInfo) {
        TrafficInfo savedTrafficInfo = trafficInfoService.saveTrafficInfo(trafficInfo);
        return new ResponseEntity<>(savedTrafficInfo, HttpStatus.CREATED);
    }

    @GetMapping("/{zipCode}")
    public ResponseEntity<List<TrafficInfo>> getTrafficInfoByZipCode(@PathVariable String zipCode) {
        List<TrafficInfo> trafficInfoList = trafficInfoService.getTrafficInfoByZipCode(zipCode);
        return ResponseEntity.ok(trafficInfoList);
    }

    @GetMapping
    public ResponseEntity<List<TrafficInfo>> getAllTrafficInfo() {
        List<TrafficInfo> allTrafficInfo = trafficInfoService.getAllTrafficInfo();
        return ResponseEntity.ok(allTrafficInfo);
    }
}