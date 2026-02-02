package com.mink.trip.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileDetail {
    private String profileWord;
    private String profileImg;
}