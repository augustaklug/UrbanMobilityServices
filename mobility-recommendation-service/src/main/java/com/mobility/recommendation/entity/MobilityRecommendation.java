package com.mobility.recommendation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mobility_recommendation")
public class MobilityRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8)
    private String zipCode;

    @Column(nullable = false)
    private String recommendedMode;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}
