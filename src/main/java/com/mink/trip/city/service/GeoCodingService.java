package com.mink.trip.city.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GeoCodingService {

    private final RestTemplate restTemplate;


    @Value("${opencage.api-key}")
    private String openCageApiKey;



    public GeoPoint geocode(String country, String city) {
        String query = (city != null && !city.isBlank()) ? (city + "," + country) : country;
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);

        String url = "https://api.opencagedata.com/geocode/v1/json?q="
                + encoded + "&key=" + openCageApiKey;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            return new GeoPoint(0.0, 0.0);
        }

        Object resultsObj = response.getBody().get("results");
        if (!(resultsObj instanceof List<?> results) || results.isEmpty()) {
            return new GeoPoint(0.0, 0.0);
        }

        Object first = results.get(0);
        if (!(first instanceof Map<?, ?> firstMap)) {
            return new GeoPoint(0.0, 0.0);
        }

        Object geometryObj = firstMap.get("geometry");
        if (!(geometryObj instanceof Map<?, ?> geometry)) {
            return new GeoPoint(0.0, 0.0);
        }

        double lat = toDouble(geometry.get("lat"));
        double lng = toDouble(geometry.get("lng"));

        return new GeoPoint(lat, lng);
    }

    private double toDouble(Object v) {
        if (v instanceof Number n) return n.doubleValue();
        if (v == null) return 0.0;
        try {
            return Double.parseDouble(String.valueOf(v));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public record GeoPoint(double latitude, double longitude) {}
}
