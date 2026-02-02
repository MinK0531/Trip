package com.mink.trip.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyPageWishlistPoint {
    private long countryId;
    private long wishlistId;
    private double latitude;
    private double longitude;
}
