package com.mink.trip.home.service;

import com.mink.trip.home.dto.HomeDetail;
import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.post.service.PostService;
import com.mink.trip.wishlist.dto.WishlistDetail;
import com.mink.trip.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PostService postService;
    private final WishlistService wishlistService;

    public List<HomeDetail> getFeedList(Long userId) {
        if (userId == null) {
            return List.of();
        }

        List<PostDetail> posts = postService.getPostList(userId);
        List<WishlistDetail> wishes = wishlistService.getWishlistList(userId);

        List<HomeDetail> feed = new ArrayList<>();

        if (posts != null) {
            for (PostDetail p : posts) {
                if (p == null) continue;

                feed.add(HomeDetail.builder()
                        .type(HomeDetail.Type.POST)
                        .createdAt(p.getCreatedAt())
                        .post(p)
                        .build());
            }
        }

        if (wishes != null) {
            for (WishlistDetail w : wishes) {
                if (w == null) continue;

                feed.add(HomeDetail.builder()
                        .type(HomeDetail.Type.WISHLIST)
                        .createdAt(w.getCreatedAt())
                        .wishlist(w)
                        .build());
            }
        }

        feed.sort(Comparator.comparing(
                HomeDetail::getCreatedAt,
                Comparator.nullsLast(Comparator.reverseOrder())
        ));

        return feed;
    }
}
