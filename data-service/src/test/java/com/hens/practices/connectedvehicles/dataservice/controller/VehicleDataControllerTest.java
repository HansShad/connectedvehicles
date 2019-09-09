package com.hens.practices.connectedvehicles.dataservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hens.practices.connectedvehicles.dataservice.entity.Vehicle;
import com.hens.practices.connectedvehicles.dataservice.service.VehicleDataService;
import com.hens.practices.connectedvehicles.dataservice.testutility.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VehicleDataController.class)
class VehicleDataControllerTest {

    private static final String HTTP_CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String CONNECTED_STATUS = "Connected";
    private static final String CUSTOMER_1_NAME = "CUSTOMER1";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleDataService vehicleDataService;

    private List<Vehicle> vehicleList;

    @BeforeEach
    void init() {

        vehicleList = TestUtil.generateListOfVehicles();
    }

    @Test
    void getAllVehicles() throws Exception {

        // when
        when(vehicleDataService.findAllVehicles()).thenReturn(vehicleList);

        // then
       MvcResult mvcResult = mockMvc.perform(get("/vehicles/findall"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(HTTP_CONTENT_TYPE))
               .andReturn();

       verify(vehicleDataService, times(1)).findAllVehicles();

        Assertions.assertThat(objectMapper.writeValueAsString(vehicleList)).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAllVehiclesByCustomer() throws Exception {

        // when
        when(vehicleDataService.findVehiclesByCustomer(CUSTOMER_1_NAME)).thenReturn(vehicleList);

        // then
        MvcResult mvcResult = mockMvc.perform(get("/vehicles/customers")
                .param("customer", CUSTOMER_1_NAME))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                    .andReturn();

        ArgumentCaptor<String> customerNameCaptor = ArgumentCaptor.forClass(String.class);
        verify(vehicleDataService, times(1)).findVehiclesByCustomer(customerNameCaptor.capture());

        Assertions.assertThat(objectMapper.writeValueAsString(vehicleList)).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAllVehiclesByStatus() throws Exception {

        // when
        when(vehicleDataService.findVehiclesByStatus(CONNECTED_STATUS)).thenReturn(vehicleList);

        // then
        MvcResult mvcResult = mockMvc.perform(get("/vehicles/statuses")
            .param("status", CONNECTED_STATUS))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                    .andReturn();

        ArgumentCaptor<String> vehicleStatusCaptor = ArgumentCaptor.forClass(String.class);
        verify(vehicleDataService, times(1)).findVehiclesByStatus(vehicleStatusCaptor.capture());

        Assertions.assertThat(objectMapper.writeValueAsString(vehicleList)).isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAllCustomers() throws Exception {

        // when
        when(vehicleDataService.findDistinctCustomer()).thenReturn(TestUtil.listOfCustomers());

        // then
        MvcResult mvcResult = mockMvc.perform(get("/vehicles/customers/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                .andReturn();

        verify(vehicleDataService, times(1)).findDistinctCustomer();

        Assertions.assertThat(objectMapper.writeValueAsString(TestUtil.listOfCustomers())).isEqualTo(mvcResult.getResponse().getContentAsString());
    }
}