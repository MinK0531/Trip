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
    public ApiResponse<Void> likePost(
            @RequestParam long postId,
            HttpSession session){

        long userId = (Long) session.getAttribute("userId");

        if(likeService.createLikePost(postId,userId)){
            return ApiResponse.success("좋아요 성공");
        }else {
            return ApiResponse.fail("좋아요 실패");
        }
    }
    @DeleteMapping("/post/unlike")
    public ApiResponse<Void> unlikePost(
            @RequestParam long postId,
            HttpSession session){
        long userId = (Long) session.getAttribute("userId");

        if(likeService.deleteLikePost(postId,userId)){
            return ApiResponse.success("좋아요 취소 성공");
        }else {
            return ApiResponse.fail("좋아요 취소 실패");
        }
    }
    @PostMapping("/comment/like")
    public ApiResponse<Void> likeComment(
            @RequestParam long commentId,
            HttpSession session) {

        long userId = (Long) session.getAttribute("userId");

        if(likeService.createlikeComment(commentId,userId)){
            return ApiResponse.success("댓글 좋아요 성공");
        }else {
            return ApiResponse.fail("댓글 좋아요 실패");
        }
    }
    @DeleteMapping("/comment/unlike")
    public ApiResponse<Void> unlikeComment(
            @RequestParam long commentId,
            HttpSession session){
        long userId = (Long) session.getAttribute("userId");

        if(likeService.deletelikeComment(commentId,userId)){
            return ApiResponse.success("댓글 좋아요 취소 성공");
        }else {
            return ApiResponse.fail("댓글 좋아요 취소 실패");
        }
    }
}
