package com.mink.trip.post.dto;

import com.mink.trip.comment.dto.CommentDetail;
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
    private String nickName;

    private long countryId;
    private String countryName;
    private String cityName;
    private String contents;
    private String atmosphere;
    private String placeName;
    private String musicUrl;
    private double latitude;
    private double longitude;

    private int likeCount;
    private  boolean isLike;

    private LocalDateTime createdAt;
    private List<PostImageDetail> imageList;

    private int commentCount;
    private List<CommentDetail> commentList;
}
