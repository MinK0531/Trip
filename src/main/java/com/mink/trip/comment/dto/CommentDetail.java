package com.mink.trip.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentDetail {
    private Long id;
    private Long userId;
    private String nickName;
    private String comments;

    private Long parentId;
    private Long rootId;
    private int depth;
    private boolean isDeleted;

    private int likeCount;
    private boolean isLike;
}