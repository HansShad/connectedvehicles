package com.hens.practices.connectedvehicles.vehiclesimulatorservice.controller;

import com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity.VehicleData;
import com.hens.practices.connectedvehicles.vehiclesimulatorservice.service.VehicleSimulatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class SimulatorController {

    private static final String DISCONNECTED = "Disconnected";

    private final VehicleSimulatorService vehicleSimulatorService;

    @GetMapping("/ping")
    public String pingVehicleForStatus(@RequestParam("id") Long id) {

        if (VehicleData.getVehicles().size() > 0) {
            vehicleSimulatorService.randomizeUnreachableVehicles();
        }
        else {
            vehicleSimulatorService.fetchAllVehicles();
        }

        return VehicleData.getVehicles()
                .stream()
                .filter(vehicle -> vehicle.getId() == id)
                .findFirst()
                .map(vehicle -> vehicle.getPingable() ? vehicle.getStatus() : DISCONNECTED )
                .orElse(DISCONNECTED);

    }

}
