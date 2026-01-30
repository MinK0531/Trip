package com.mink.trip.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyPagePostPoint {
    private long postId;
    private double latitude;
    private double longitude;
}