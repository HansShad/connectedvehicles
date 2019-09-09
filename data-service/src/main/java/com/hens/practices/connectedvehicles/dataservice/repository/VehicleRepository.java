package com.hens.practices.connectedvehicles.dataservice.repository;

import com.hens.practices.connectedvehicles.dataservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCustomer(String customer);
    List<Vehicle> findByStatus(String status);

    @Query("select distinct customer from Vehicle")
    List<String> findDistinctCustomer();
}
