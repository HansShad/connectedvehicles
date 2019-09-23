package com.hens.practices.connectedvehicles.vehiclesimulatorservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hens.practices.connectedvehicles.vehiclesimulatorservice.entity.VehicleDataUtil;
import com.hens.practices.connectedvehicles.vehiclesimulatorservice.service.VehicleSimulatorService;
import com.hens.practices.connectedvehicles.vehiclesimulatorservice.testutility.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = SimulatorController.class)
class SimulatorControllerTest {

    private static final String HTTP_CONTENT_TYPE = "text/plain;charset=UTF-8";

    @MockBean
    private VehicleSimulatorService vehicleSimulatorService;

    @Autowired
    private MockMvc mockMvc;

    @AfterAll
    private void tearDown() {

        VehicleDataUtil.setVehicles(null);
    }

    @Test
    public void pingConnectedAndPingableVehicleForStatus() throws Exception {

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                VehicleDataUtil.setVehicles(TestUtil.generateListOfVehicles());
                return null;
            }
        }).when(vehicleSimulatorService)
                .fetchAllVehicles();

        MvcResult mvcResult = mockMvc.perform(get("/vehicles/ping?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                .andReturn();

        Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Connected");
    }

    @Test
    public void pingConnectedAndNotPingableVehicleForStatus() throws Exception {

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                VehicleDataUtil.setVehicles(TestUtil.generateListOfVehicles());
                return null;
            }
        }).when(vehicleSimulatorService)
                .fetchAllVehicles();

        MvcResult mvcResult = mockMvc.perform(get("/vehicles/ping?id=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                .andReturn();

        Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Disconnected");
    }

    @Test
    public void pingDisconnectedAndPingableVehicleForStatus() throws Exception {

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                VehicleDataUtil.setVehicles(TestUtil.generateListOfVehicles());
                return null;
            }
        }).when(vehicleSimulatorService)
                .fetchAllVehicles();

        MvcResult mvcResult = mockMvc.perform(get("/vehicles/ping?id=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(HTTP_CONTENT_TYPE))
                .andReturn();

        Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("Disconnected");
    }
}
