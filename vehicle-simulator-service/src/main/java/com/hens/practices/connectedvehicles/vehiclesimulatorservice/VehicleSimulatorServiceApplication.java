package com.hens.practices.connectedvehicles.vehiclesimulatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class VehicleSimulatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleSimulatorServiceApplication.class, args);
	}

}
