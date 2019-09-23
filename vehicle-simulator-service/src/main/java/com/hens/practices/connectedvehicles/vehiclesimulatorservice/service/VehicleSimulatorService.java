package com.hens.practices.connectedvehicles.vehiclesimulatorservice.service;

import com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity.Vehicle;
import com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity.VehicleDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Component
public class VehicleSimulatorService {

    @Autowired
    private RestTemplate restTemplate;

    public void fetchAllVehicles() {

        ResponseEntity<List<Vehicle>> response = restTemplate.exchange("http://data-service/vehicles/findall", HttpMethod.GET, null, new ParameterizedTypeReference<List<Vehicle>>() {});

        List<Vehicle> vehicleList = response.getStatusCode() == HttpStatus.OK
                ? response.getBody()
                : Collections.EMPTY_LIST;

        VehicleDataUtil.setVehicles(vehicleList);
        randomizeUnreachableVehicles();
    }

    public void randomizeUnreachableVehicles() {

        Consumer<Vehicle> configurePingable = new Consumer<Vehicle>() {
            @Override
            public void accept(Vehicle vehicle) {

//                (Math.random() * ((max - min) + 1)) + min
                int randomValue = (int)(Math.random() * 2 + 0);
                if (randomValue == 0) {
                    vehicle.setPingable(false);
                }
                else {
                    vehicle.setPingable(true);
                }
            }
        };

        VehicleDataUtil.getVehicles()
                .stream()
                .forEach(configurePingable);
    }

    /**
     * This method randomly updates the vehicle status
     */
    @Scheduled(fixedDelay = 6000)
    protected void simulateVehicleStatusChange() {

        Consumer<Vehicle> simulateStatusUpdate = new Consumer<Vehicle>() {
            @Override
            public void accept(Vehicle vehicle) {

                int randomValue = (int)(Math.random() * 2 + 0);
                if (randomValue == 0) {
                    vehicle.setStatus("Disconnected");
                } else {
                    vehicle.setStatus("Connected");
                }
            }
        };

        VehicleDataUtil.getVehicles()
                .stream()
                .forEach(simulateStatusUpdate);
    }
}
