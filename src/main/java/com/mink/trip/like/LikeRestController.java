package com.mink.trip.like;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.like.service.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LikeRestController {
    private final LikeService likeService;

    @PostMapping("/post/like")
    public ApiResponse<Void> like(
            @RequestParam long postId,
            HttpSession session){

        long userId = (Long) session.getAttribute("userId");

        if(likeService.createLike(postId,userId)){
            return ApiResponse.success("좋아요 성공");
        }else {
            return ApiResponse.fail("좋아요 실패");
        }
    }
    @DeleteMapping("/post/unlike")
    public ApiResponse<Void> unlike(
            @RequestParam long postId,
            HttpSession session){
        long userId = (Long) session.getAttribute("userId");

        if(likeService.deleteLike(postId,userId)){
            return ApiResponse.success("좋아요 취소 성공");
        }else {
            return ApiResponse.fail("좋아요 취소 실패");
        }
    }

}
