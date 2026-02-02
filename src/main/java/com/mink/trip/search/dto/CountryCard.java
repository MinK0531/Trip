package com.mink.trip.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CountryCard {
    private long countryId;
    private String countryName;
    private List<String> cities;
}