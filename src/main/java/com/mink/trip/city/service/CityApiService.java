package com.mink.trip.city.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CityApiService {

    private final RestTemplate restTemplate;



    public List<String> fetchCities(String country) {
        String url = "https://countriesnow.space/api/v0.1/countries/cities";

        Map<String, String> body = new HashMap<>();
        body.put("country", country);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            return Collections.emptyList();
        }

        Object dataObj = response.getBody().get("data");
        if (dataObj instanceof List<?> list) {
            List<String> cities = new ArrayList<>();
            for (Object o : list) {
                if (o != null) cities.add(String.valueOf(o));
            }
            return cities;
        }

        return Collections.emptyList();
    }
}
