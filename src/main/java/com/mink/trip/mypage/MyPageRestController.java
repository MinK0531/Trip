package com.mink.trip.mypage;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.mypage.dto.MyPagePostPoint;
import com.mink.trip.mypage.service.MyPageService;
import com.mink.trip.post.dto.PostDetail;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage/api/posts")
public class MyPageRestController {

    private final MyPageService myPageService;

    @GetMapping("/points")
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

    @GetMapping("/detail")
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
