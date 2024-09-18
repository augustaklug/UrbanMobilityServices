package com.mobility.trafficinfo.repository;

import com.mobility.trafficinfo.entity.TrafficInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficInfoRepository extends JpaRepository<TrafficInfo, Long> {
    List<TrafficInfo> findByZipCodeOrderByTimestampDesc(String zipCode);
}
