package com.mink.trip.mypage;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.mypage.dto.MyPagePostPoint;
import com.mink.trip.mypage.dto.MyPageWishlistPoint;
import com.mink.trip.mypage.service.MyPageService;
import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.post.service.PostService;
import com.mink.trip.wishlist.dto.WishlistDetail;
import com.mink.trip.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage/api")
public class MyPageRestController {
    private final PostService postService;
    private final MyPageService myPageService;
    private final WishlistService wishlistService;
    @GetMapping("/post/listByCountry")
    public ApiResponse<List<PostDetail>> postListByCountry(
            @RequestParam Long countryId,
            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");

        List<PostDetail> data = postService.listByCountry(userId,countryId);

        return ApiResponse.success("게시물 조회 성공", data);
    }

    @GetMapping("/wishlist/listByCountry")
    public ApiResponse<List<WishlistDetail>> wishlistListByCountry(
            @RequestParam Long countryId,
            HttpSession session) {

        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");

        List<WishlistDetail> data = wishlistService.getWishlistListByCountry(userId, countryId);

        return ApiResponse.success("위시리스트 조회 성공", data);
    }


    @GetMapping("/post/points")
    public ApiResponse<List<MyPagePostPoint>> myPoints(HttpSession session) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");
        return ApiResponse.success(
                "내 게시물 좌표 조회 성공",
                myPageService.getMyPostPoints(userId)
        );
    }
    @GetMapping("/wishlist/points")
    public ApiResponse<List<MyPageWishlistPoint>> myWishlistPoints(HttpSession session) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");
        return ApiResponse.success(
                "내 위시리스트 좌표 조회 성공",
                myPageService.getMyWishlistPoints(userId)
        );
    }



    @GetMapping("/post/detail")
    public ApiResponse<PostDetail> myDetail(
            @RequestParam long postId,
            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");
        PostDetail detail = myPageService.getMyPostDetail(userId, postId);

        if (detail == null) {
            return ApiResponse.fail("조회 실패");
        }

        return ApiResponse.success("내 게시물 상세 조회 성공", detail);
    }
}
