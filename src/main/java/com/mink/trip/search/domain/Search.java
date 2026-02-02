package com.mink.trip.search.domain;

import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.search.dto.CountryCard;
import com.mink.trip.search.dto.UserCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Search{
    private List<UserCard> friends;
    private List<UserCard> users;
    private List<CountryCard> countries;
    private List<PostDetail> posts;
}