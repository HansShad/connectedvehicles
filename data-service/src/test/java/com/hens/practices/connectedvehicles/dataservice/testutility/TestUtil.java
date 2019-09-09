package com.hens.practices.connectedvehicles.dataservice.testutility;

import com.hens.practices.connectedvehicles.dataservice.entity.Vehicle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {

    public static List<Vehicle> generateListOfVehicles() {

        Vehicle vehicle1 = new Vehicle("VIN1", "REGNR1", "CUSTOMER1", "ADDRESS1", "Connected");
        Vehicle vehicle2 = new Vehicle("VIN2", "REGNR2", "CUSTOMER2", "ADDRESS2", "Disconnected");
        return Arrays.asList(vehicle1, vehicle2);
    }

    public static List<String> listOfCustomers() {

        return generateListOfVehicles().stream()
                .map(vehicle -> vehicle.getCustomer())
                .collect(Collectors.toList());
    }
}
