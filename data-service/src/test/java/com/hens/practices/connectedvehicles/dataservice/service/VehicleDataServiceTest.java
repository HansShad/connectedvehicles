package com.hens.practices.connectedvehicles.dataservice.service;

import com.hens.practices.connectedvehicles.dataservice.entity.Vehicle;
import com.hens.practices.connectedvehicles.dataservice.repository.VehicleRepository;
import com.hens.practices.connectedvehicles.dataservice.testutility.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
class VehicleDataServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleDataService vehicleDataService;

    private List<Vehicle> vehicleList;

    @BeforeEach
    void init() {

        vehicleList = TestUtil.generateListOfVehicles();
    }

    @Test
    void findVehiclesByCustomer() {

        // when
        when(vehicleRepository.findByCustomer("CUSTOMER1")).thenReturn(vehicleList);

        // then
        assertThat(vehicleDataService.findVehiclesByCustomer("CUSTOMER1")).contains(vehicleList.get(0));
    }

    @Test
    void findVehiclesByStatus() {

        // when
        when(vehicleRepository.findByStatus("Connected")).thenReturn(vehicleList);

        // then
        assertThat(vehicleDataService.findVehiclesByStatus("Connected")).contains(vehicleList.get(0));

    }

    @Test
    void findAllVehicles() {

        // when
        when(vehicleRepository.findAll()).thenReturn(vehicleList);

        // then
        assertThat(vehicleDataService.findAllVehicles()).contains(vehicleList.get(0));
        assertThat(vehicleDataService.findAllVehicles()).hasSize(vehicleList.size());
    }

    @Test
    void findDistinctCustomer() {

        // when
        when(vehicleRepository.findDistinctCustomer()).thenReturn(TestUtil.listOfCustomers());

        // then
        assertThat(vehicleDataService.findDistinctCustomer()).hasSize(2);
        assertThat(vehicleDataService.findDistinctCustomer().get(0)).isEqualTo("CUSTOMER1");
        assertThat(vehicleDataService.findDistinctCustomer().get(1)).isEqualTo("CUSTOMER2");
    }
}