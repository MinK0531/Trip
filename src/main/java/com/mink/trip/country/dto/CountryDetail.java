package com.mink.trip.country.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryDetail {
    private long id;

    private String countryName;
    private String countryNameEn;
    private String countryCode;
}
