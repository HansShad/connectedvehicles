package com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity;

import java.util.ArrayList;
import java.util.List;

public class VehicleDataUtil {

    private static List<Vehicle> vehicles = new ArrayList<>();

    public static List<Vehicle> getVehicles() {
        return vehicles;
    }

    public static void setVehicles(List<Vehicle> vehicles) {
        VehicleDataUtil.vehicles = vehicles;
    }
}
