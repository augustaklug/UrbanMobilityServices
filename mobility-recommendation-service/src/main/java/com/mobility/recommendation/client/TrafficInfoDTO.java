package com.mobility.recommendation.client;

import lombok.Data;

@Data
public class TrafficInfoDTO {
    private Long id;
    private String zipCode;
    private Integer congestionLevel;
    private String timestamp;
}
