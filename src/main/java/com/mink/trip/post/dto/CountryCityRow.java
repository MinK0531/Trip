package com.mink.trip.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryCityRow {
    private long countryId;
    private String countryName;
    private String cityName;
}
