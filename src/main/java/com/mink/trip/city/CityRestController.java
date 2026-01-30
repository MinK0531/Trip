package com.mink.trip.city;

import com.mink.trip.city.dto.CitySearchRequest;
import com.mink.trip.city.service.CityApiService;
import com.mink.trip.city.service.GeoCodingService;
import com.mink.trip.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityRestController {

    private final CityApiService cityApiService;
    private final GeoCodingService geoCodingService;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCitiesAndCoords(@RequestBody CitySearchRequest request) {
        String country = request.getCountry();
        String city = request.getCity();

        if (country == null || country.isBlank()) {
            return ResponseEntity.ok(ApiResponse.fail("country는 필수이다"));
        }

        try {
            List<String> cities = cityApiService.fetchCities(country);
            GeoCodingService.GeoPoint point = geoCodingService.geocode(country, city);

            Map<String, Object> result = new HashMap<>();
            result.put("cities", cities);
            result.put("latitude", point.latitude());
            result.put("longitude", point.longitude());

            return ResponseEntity.ok(ApiResponse.success("도시 및 좌표 조회 성공", result));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.fail("서버 에러: " + e.getMessage()));
        }
    }
}
