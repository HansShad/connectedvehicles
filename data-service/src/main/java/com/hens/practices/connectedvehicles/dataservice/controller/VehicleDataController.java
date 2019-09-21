package com.hens.practices.connectedvehicles.dataservice.controller;

import com.hens.practices.connectedvehicles.dataservice.entity.Vehicle;
import com.hens.practices.connectedvehicles.dataservice.service.VehicleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleDataController {

    private final VehicleDataService vehicleDataService;


    @GetMapping("/findall")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {

        return new ResponseEntity(vehicleDataService.findAllVehicles(), HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Vehicle>> getAllVehiclesByCustomer(@RequestParam("customer") String name) {

        return new ResponseEntity<>(vehicleDataService.findVehiclesByCustomer(name), HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<Vehicle>> getAllVehiclesByStatus(@RequestParam("status") String status) {

        return new ResponseEntity<>(vehicleDataService.findVehiclesByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/customers/all")
    public ResponseEntity<List<String>> getAllCustomers() {

        return new ResponseEntity<>(vehicleDataService.findDistinctCustomer(), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateVehicleStatus(@PathVariable("id") long id, @RequestBody String vehicleNewStatus) {

        vehicleDataService.updateVehicleStatus(id, vehicleNewStatus);
        return new ResponseEntity(HttpStatus.OK);
    }
}
