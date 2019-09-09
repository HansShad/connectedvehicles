package com.hens.practices.connectedvehicles.uiservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hens.practices.connectedvehicles.uiservice.entity.Vehicle;
import com.hens.practices.connectedvehicles.uiservice.service.UiService;
import com.hens.practices.connectedvehicles.uiservice.testutility.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VehicleUiController.class)
class VehicleUiControllerTest {

    private static final String HTTP_CONTENT_TYPE = "text/html;charset=UTF-8";
    private static final String CONNECTED_STATUS = "Connected";
    private static final String CUSTOMER_1 = "CUSTOMER1";

    private List<Vehicle> vehicleList;
    private List<String> customerList;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UiService uiService;

    @BeforeEach
    void setup() {

        vehicleList = TestUtil.generateListOfVehicles();
        customerList = TestUtil.listOfCustomers();
    }

    @Test
    void getAllVehicles() throws Exception {

        // when
        when(uiService.performRequest("http://data-service/vehicles/findall", HttpMethod.GET, null, Vehicle.class))
                .thenReturn(vehicleList);
        when(uiService.performRequest("http://data-service/vehicles/customers/all", HttpMethod.GET, null, String.class))
                .thenReturn(customerList);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/vehicles/find/findall"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                .andReturn();

        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpMethod> httpMethodCaptor = ArgumentCaptor.forClass(HttpMethod.class);
        ArgumentCaptor<HttpEntity> httpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(uiService, times(2)).performRequest(uriCaptor.capture(), httpMethodCaptor.capture(), httpEntityCaptor.capture(), any());
    }

    @Test
    void getVehicleByStatus() throws Exception {

        // when
        when(uiService.performRequest("http://data-service/vehicles/statuses?status=" + CONNECTED_STATUS, HttpMethod.GET, null, Vehicle.class))
                .thenReturn(vehicleList.stream()
                        .filter(v -> v.getStatus().equals(CONNECTED_STATUS))
                        .collect(Collectors.toList()));
        when(uiService.performRequest("http://data-service/vehicles/customers/all", HttpMethod.GET, null, String.class))
                .thenReturn(customerList);

        // then
        MvcResult mvcResult = mockMvc.perform(get("/vehicles/find/findbystatus")
            .param("status", CONNECTED_STATUS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                .andDo(print())
                .andReturn();

        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpMethod> httpMethodCaptor = ArgumentCaptor.forClass(HttpMethod.class);
        ArgumentCaptor<HttpEntity> httpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(uiService, times(2)).performRequest(uriCaptor.capture(), httpMethodCaptor.capture(), httpEntityCaptor.capture(), any());
    }

    @Test
    void getVehiclesByCustomer() throws Exception {

        // when
        when(uiService.performRequest("http://data-service/vehicles/customers?customer=" + CUSTOMER_1, HttpMethod.GET, null, Vehicle.class))
                .thenReturn(vehicleList.stream()
                .filter(v -> v.getCustomer().equals(CUSTOMER_1))
                .collect(Collectors.toList()));
        when(uiService.performRequest("http://data-service/vehicles/customers/all", HttpMethod.GET, null, String.class))
                .thenReturn(customerList);

        // then
        MvcResult mvcResult = mockMvc.perform(get("/vehicles/find/findbycustomer")
            .param("customer", CUSTOMER_1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                .andDo(print())
                .andReturn();

        ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpMethod> httpMethodCaptor = ArgumentCaptor.forClass(HttpMethod.class);
        ArgumentCaptor<HttpEntity> httpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(uiService, times(2)).performRequest(uriCaptor.capture(), httpMethodCaptor.capture(), httpEntityCaptor.capture(), any());
    }
}