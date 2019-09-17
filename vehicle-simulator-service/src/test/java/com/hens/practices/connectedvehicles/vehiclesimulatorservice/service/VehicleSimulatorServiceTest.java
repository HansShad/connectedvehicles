package com.hens.practices.connectedvehicles.vehiclesimulatorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity.Vehicle;
import com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity.VehicleData;
import com.hens.practices.connectedvehicles.vehiclesimulatorservice.testutility.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("Test")
@RunWith(SpringRunner.class)
@SpringBootTest
class VehicleSimulatorServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VehicleSimulatorService vehicleSimulatorService;

    private MockRestServiceServer mockeServer;

    @BeforeEach
    private void init() {

        mockeServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void fetchAllVehicles() throws JsonProcessingException {

        mockeServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo("http://data-service/vehicles/findall"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(objectMapper.writeValueAsString(TestUtil.generateListOfVehicles()), MediaType.APPLICATION_JSON));

        vehicleSimulatorService.fetchAllVehicles();
        mockeServer.verify();

//        chekcing only the Id
        Assertions.assertThat(objectMapper.writeValueAsString(VehicleData.getVehicles().get(1).getId())).isEqualTo(objectMapper.writeValueAsString(TestUtil.generateListOfVehicles().get(1).getId()));

    }
}