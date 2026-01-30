package com.mink.trip.home.dto;

import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.wishlist.dto.WishlistDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class HomeDetail {
    public enum Type { POST, WISHLIST }

    private Type type;
    private LocalDateTime createdAt;

    private PostDetail post;
    private WishlistDetail wishlist;
}
