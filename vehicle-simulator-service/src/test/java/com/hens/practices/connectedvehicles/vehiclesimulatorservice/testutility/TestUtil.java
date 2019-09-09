package com.hens.practices.connectedvehicles.vehiclesimulatorservice.testutility;

import com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity.Vehicle;

import java.util.Arrays;
import java.util.List;

public class TestUtil {

    public static List<Vehicle> generateListOfVehicles() {

        Vehicle vehicle1 = new Vehicle(1L, "Connected", true);
        Vehicle vehicle2 = new Vehicle(2L, "Disconnected", true);
        Vehicle vehicle3 = new Vehicle(3L, "Connected", false);
        return Arrays.asList(vehicle1, vehicle2);
    }
}
