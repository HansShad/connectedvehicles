package com.hens.practices.connectedvehicles.vehicledatamanagerservice.service;

import com.hens.practices.connectedvehicles.vehicledatamanagerservice.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class VehicleManagementService {

    @Autowired
    private RestTemplate restTemplate;


    @Scheduled(fixedDelay = 5000)
    private void pingAllVehicles() {

        ResponseEntity<List<Vehicle>> vehicleListResponse = restTemplate.exchange("http://data-service/vehicles/findall", HttpMethod.GET, null, new ParameterizedTypeReference<List<Vehicle>>() {});
        if (vehicleListResponse.getStatusCode() == HttpStatus.OK) {

            vehicleListResponse.getBody()
                    .stream()
                    .forEach(this::pingVehicle);
        }
        else {
            System.out.println("PROBLEM>>>>>>>>>");
        }

    }

    private void pingVehicle(Vehicle theVehicle) {

//        String vehiclePingStatus = restTemplate.getForObject("http://vehicle-simulator-service/vehicles/ping?id=" + theVehicle.getId(), String.class);
        ResponseEntity<String> vehiclePingStatusResponseEntity = restTemplate.exchange("http://vehicle-simulator-service/vehicles/ping?id=" + theVehicle.getId(), HttpMethod.GET, null, String.class);
        if (vehiclePingStatusResponseEntity.getStatusCode() == HttpStatus.OK) {

            String vehiclePingStatus = vehiclePingStatusResponseEntity.getBody();
            if (vehiclePingStatus.isBlank() || !theVehicle.getStatus().equals(vehiclePingStatus)) {
                updateVehicleStatus(theVehicle.getId(), vehiclePingStatus);
            }
        }
    }

    private void updateVehicleStatus(long id, String newStatus) {

        // Update database with new status
    }
}
