package com.hens.practices.connectedvehicles.uiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class UiService {

    @Autowired
    private RestTemplate restTemplate;

    public <T> List<T> performRequest(String uri, HttpMethod httpMethod, HttpEntity httpEntity, Class<T> clazz) {

        ResponseEntity<List<T>> responseEntity =
                restTemplate.exchange(uri, httpMethod, httpEntity, ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(List.class, clazz).getType()));

        return responseEntity.getStatusCode() == HttpStatus.OK
                ? responseEntity.getBody()
                : Collections.emptyList();
    }
}
