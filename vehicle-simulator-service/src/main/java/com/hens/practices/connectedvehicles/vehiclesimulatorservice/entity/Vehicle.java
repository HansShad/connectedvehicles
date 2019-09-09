package com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity;

import java.util.Objects;

public class Vehicle {

    private long id;
    private String status;
    private Boolean pingable;

    public Vehicle() {
    }

    public Vehicle(long id, String status, Boolean pingable) {
        this.id = id;
        this.status = status;
        this.pingable = pingable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getPingable() {
        return pingable;
    }

    public void setPingable(Boolean pingable) {
        this.pingable = pingable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id &&
                status.equals(vehicle.status) &&
                pingable.equals(vehicle.pingable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, pingable);
    }
}