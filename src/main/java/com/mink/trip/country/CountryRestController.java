package com.mink.trip.country;

import com.mink.trip.country.dto.CountryDetail;
import com.mink.trip.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryRestController {

    private final CountryService countryService;

    @GetMapping("/list")
    public List<CountryDetail> getCountryList() {
        return countryService.getCountryList().stream()
                .map(c -> new CountryDetail(
                        c.getId(),
                        c.getCountryName(),
                        c.getCountryNameEn(),
                        c.getCountryCode()))
                .toList();

    }

}