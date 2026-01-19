package com.mink.trip.country.service;

import com.mink.trip.country.domain.Country;
import com.mink.trip.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public List<Country> getCountryList() {
        return countryRepository.findAllByOrderByCountryNameAsc();
    }



}
