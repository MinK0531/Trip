package com.mink.trip.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class WishlistDetail {
    private long id;

    private Long userId;
    private String nickName;
    private long countryId;
    private String countryName;

    private String cityName;
    private String period;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memo;

    private Double latitude;
    private Double longitude;

    private LocalDateTime createdAt;
}
