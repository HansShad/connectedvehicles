package com.hens.practices.connectedvehicles.vehicledatamanagerservice.service;

import com.hens.practices.connectedvehicles.vehicledatamanagerservice.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class VehicleManagementService {

    @Autowired
    private RestTemplate restTemplate;


    @Scheduled(fixedDelay = 5000)
    protected void pingAllVehicles() {

        ResponseEntity<List<Vehicle>> vehicleListResponse = restTemplate.exchange("http://data-service/vehicles/findall", HttpMethod.GET, null, new ParameterizedTypeReference<List<Vehicle>>() {});
        if (vehicleListResponse.getStatusCode() == HttpStatus.OK) {

            vehicleListResponse.getBody()
                    .stream()
                    .forEach(this::pingVehicle);
        }
    }

    private void pingVehicle(Vehicle theVehicle) {

        ResponseEntity<String> vehiclePingStatusResponseEntity = restTemplate.exchange("http://vehicle-simulator-service/vehicles/ping?id=" + theVehicle.getId(), HttpMethod.GET, null, String.class);
        if (vehiclePingStatusResponseEntity.getStatusCode() == HttpStatus.OK) {

            String vehiclePingStatus = vehiclePingStatusResponseEntity.getBody();
//            Check vehicle status and update the db if vehicle status is different
            if (!theVehicle.getStatus().equals(vehiclePingStatus)) {
                updateVehicleStatus(theVehicle.getId(), vehiclePingStatus);
            }
        }
    }

    private void updateVehicleStatus(long id, String newStatus) {

        HttpEntity<String> requestEntity = new HttpEntity<>(newStatus);
        ResponseEntity<Void> updateVehicleResponse = restTemplate.exchange("http://data-service/vehicles/update/" + id, HttpMethod.POST, requestEntity, Void.class);
    }
}
