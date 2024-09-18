package com.mobility.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MobilityRecommendationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilityRecommendationServiceApplication.class, args);
	}

}
