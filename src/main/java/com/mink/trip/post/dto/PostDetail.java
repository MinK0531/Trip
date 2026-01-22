package com.mink.trip.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class PostDetail {
    private long id;
    private long userId;
    private String signinId;

    private long countryId;
    private String countryName;
    private String cityName;
    private String contents;
    private String atmosphere;
    private String placeName;
    private String musicUrl;
    private double latitude;
    private double longitude;
    private LocalDateTime createdAt;
    private List<PostImageDetail> imageList;
}
