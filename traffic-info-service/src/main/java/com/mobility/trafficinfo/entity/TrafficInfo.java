package com.mobility.trafficinfo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "traffic_info")
public class TrafficInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8)
    private String zipCode;

    @Column(nullable = false)
    private Integer congestionLevel;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
