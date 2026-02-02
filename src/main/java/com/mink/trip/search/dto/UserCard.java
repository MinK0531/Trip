package com.mink.trip.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserCard {
    private long userId;
    private String nickName;
    private String profileImg;
    private String profileWord;
}
