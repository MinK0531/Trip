package com.mink.trip.city;

import com.mink.trip.city.dto.CityRequest;
import com.mink.trip.common.dto.ApiResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/city")
public class CityRestController {

    private final RestTemplate restTemplate;

    public CityRestController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @PostMapping("/list")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCitiesAndCoords(@RequestBody CityRequest request) {
        String country = request.getCountry();
        String city = request.getCity();

        Map<String, Object> result = new HashMap<>();

        try {
            String citiesUrl = "https://countriesnow.space/api/v0.1/countries/cities";
            Map<String, String> body = new HashMap<>();
            body.put("country", country);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> citiesResponse = restTemplate.postForEntity(citiesUrl, entity, Map.class);
            List<String> cities = new ArrayList<>();
            if (citiesResponse.getStatusCode() == HttpStatus.OK && citiesResponse.getBody() != null) {
                Object dataObj = citiesResponse.getBody().get("data");
                if (dataObj instanceof List) {
                    cities = (List<String>) dataObj;
                }
            }
            result.put("cities", cities);

            String query = (city != null && !city.isEmpty()) ? city + "," + country : country;
            String geoUrl = "https://api.opencagedata.com/geocode/v1/json?q=" + query + "&key=" + "23e67148cb524bb8a775e0ce4108b567";

            ResponseEntity<Map> geoResponse = restTemplate.getForEntity(geoUrl, Map.class);

            double latitude = 0.0;
            double longitude = 0.0;

            if (geoResponse.getStatusCode() == HttpStatus.OK && geoResponse.getBody() != null) {
                List<Map<String, Object>> results = (List<Map<String, Object>>) geoResponse.getBody().get("results");
                if (results != null && !results.isEmpty()) {
                    Map<String, Object> geometry = (Map<String, Object>) results.get(0).get("geometry");
                    latitude = (Double) geometry.get("lat");
                    longitude = (Double) geometry.get("lng");
                }
            }

            result.put("latitude", latitude);
            result.put("longitude", longitude);

            return ResponseEntity.ok(ApiResponse.success("도시 및 좌표 조회 성공", result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(ApiResponse.fail("서버 에러: " + e.getMessage()));
        }
    }
}
