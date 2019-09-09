package com.hens.practices.connectedvehicles.uiservice.controller;

import com.hens.practices.connectedvehicles.uiservice.entity.Vehicle;
import com.hens.practices.connectedvehicles.uiservice.service.UiService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleUiController {

    private static final String ALL_CUSTOMERS_URI = "http://data-service/vehicles/customers/all";
    private static final String DATA_SERVICE_VEHCILES_MAIN_URI = "http://data-service/vehicles";

    private final UiService uiService;

    @GetMapping("/find/findall")
    public String getAllVehicles(Model model) {

        List<Vehicle> vehicleList = uiService.performRequest(DATA_SERVICE_VEHCILES_MAIN_URI + "/findall", HttpMethod.GET, null, Vehicle.class);
        List<String> customerList = uiService.performRequest(ALL_CUSTOMERS_URI, HttpMethod.GET, null, String.class);

        model.addAttribute("vehicles", vehicleList);
        model.addAttribute("customers", customerList);
        return "vehicle-page";
    }

    @GetMapping("/find/findbystatus")
    public String getVehicleByStatus(@RequestParam("status") String status,
                                     Model model) {

        if (StringUtils.isNotEmpty(status)) {

            List<Vehicle> vehicleList = uiService.performRequest(DATA_SERVICE_VEHCILES_MAIN_URI + "/statuses?status=" + status, HttpMethod.GET, null, Vehicle.class);
            model.addAttribute("vehicles", vehicleList);
        }

        List<String> customerList = uiService.performRequest(ALL_CUSTOMERS_URI, HttpMethod.GET, null, String.class);
        model.addAttribute("customers", customerList);

        return "vehicle-page";
    }

    @GetMapping("/find/findbycustomer")
    public String getVehiclesByCustomer(@RequestParam("customer") String customer,
                                        Model model) {

        if (StringUtils.isNotEmpty(customer)) {

            List<Vehicle> vehicleList = uiService.performRequest(DATA_SERVICE_VEHCILES_MAIN_URI + "/customers?customer=" + customer, HttpMethod.GET, null, Vehicle.class);
            model.addAttribute("vehicles", vehicleList);
        }

        List<String> customerList = uiService.performRequest(ALL_CUSTOMERS_URI, HttpMethod.GET, null, String.class);
        model.addAttribute("customers", customerList);

        return "vehicle-page";
    }
}
