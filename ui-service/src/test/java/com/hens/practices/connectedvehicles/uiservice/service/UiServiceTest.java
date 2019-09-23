package com.hens.practices.connectedvehicles.uiservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hens.practices.connectedvehicles.uiservice.entity.Vehicle;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.hens.practices.connectedvehicles.uiservice.testutility.TestUtil.generateListOfVehicles;
import static com.hens.practices.connectedvehicles.uiservice.testutility.TestUtil.listOfCustomers;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("Test")
@RunWith(SpringRunner.class)
@SpringBootTest
class UiServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UiService uiService;

    private MockRestServiceServer mockServer;
    private static final String CONNECTED_STATUS = "Connected";
    private static final String CUSTOMER_1_NAME = "CUSTOMER1";

    @BeforeEach
    public void setup() {

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetRequestForAllVehicles() throws JsonProcessingException {

        mockServer.expect(once(), requestTo("http://data-service/vehicles/find/findall"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(objectMapper.writeValueAsString(generateListOfVehicles()), MediaType.APPLICATION_JSON));

        List<Vehicle> vehicleList = uiService.performRequest("http://data-service/vehicles/find/findall", HttpMethod.GET, null, Vehicle.class);
        mockServer.verify();

        Assertions.assertThat(vehicleList).isEqualTo(generateListOfVehicles());
        Assertions.assertThat(objectMapper.writeValueAsString(vehicleList)).isEqualTo(objectMapper.writeValueAsString(generateListOfVehicles()));
    }

    @Test
    public void testGetRequestForAllCustomer() throws JsonProcessingException {

        mockServer.expect(once(), requestTo("http://data-service/vehicles/customers/all"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(listOfCustomers())));

        List<String> customerList = uiService.performRequest("http://data-service/vehicles/customers/all", HttpMethod.GET, null, String.class);
        mockServer.verify();

        Assertions.assertThat(objectMapper.writeValueAsString(customerList)).isEqualTo(objectMapper.writeValueAsString(listOfCustomers()));
    }

    @Test
    public void testGetRequestForAllVehiclesWithParticularStatus() throws JsonProcessingException {

        List<Vehicle> listOfConnectedVehicles = generateListOfVehicles()
                .stream()
                .filter(v -> v.getStatus().equals(CONNECTED_STATUS))
                .collect(Collectors.toList());

        mockServer.expect(once(), requestTo("http://data-service/vehicles/find/findbystatus"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(objectMapper.writeValueAsString(listOfConnectedVehicles), MediaType.APPLICATION_JSON));

        List<Vehicle> vehicleList = uiService.performRequest("http://data-service/vehicles/find/findbystatus", HttpMethod.GET, null, Vehicle.class);
        mockServer.verify();

        Assertions.assertThat(objectMapper.writeValueAsString(vehicleList)).isEqualTo(objectMapper.writeValueAsString(listOfConnectedVehicles));
    }

    @Test
    public void testGetRequestForAllVehiclesBelongToACustomer() throws JsonProcessingException {

        List<Vehicle> listOfVehicleForACustomer = generateListOfVehicles()
                .stream()
                .filter(v -> v.getCustomer().equals(CUSTOMER_1_NAME))
                .collect(Collectors.toList());

        mockServer.expect(once(), requestTo("http://data-service/vehicles/find/findbycustomer"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(objectMapper.writeValueAsString(listOfVehicleForACustomer), MediaType.APPLICATION_JSON));

        List<Vehicle> vehicleList = uiService.performRequest("http://data-service/vehicles/find/findbycustomer", HttpMethod.GET, null, Vehicle.class);
        mockServer.verify();

        Assertions.assertThat(objectMapper.writeValueAsString(vehicleList)).isEqualTo(objectMapper.writeValueAsString(listOfVehicleForACustomer));
    }
}