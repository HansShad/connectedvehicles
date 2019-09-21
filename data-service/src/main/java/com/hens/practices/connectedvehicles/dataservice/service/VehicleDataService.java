package com.hens.practices.connectedvehicles.dataservice.service;

import com.hens.practices.connectedvehicles.dataservice.entity.Vehicle;
import com.hens.practices.connectedvehicles.dataservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VehicleDataService {

    private final VehicleRepository vehicleRepository;

    public List<Vehicle> findVehiclesByCustomer(String customer) {
        return vehicleRepository.findByCustomer(customer);
    }

    public List<Vehicle> findVehiclesByStatus(String status) {

        return vehicleRepository.findByStatus(status);
    }

    public List<Vehicle> findAllVehicles() {

        return vehicleRepository.findAll();
    }

    public List<String> findDistinctCustomer() {

        return vehicleRepository.findDistinctCustomer();
    }

    public void updateVehicleStatus(long id, String newStatus) {

        Vehicle vehicleToUpdateStatus = vehicleRepository.findById(id).get();
        vehicleToUpdateStatus.setStatus(newStatus);
        vehicleRepository.save(vehicleToUpdateStatus);
//        updateVehicleStatus(id, newStatus);
    }
}
