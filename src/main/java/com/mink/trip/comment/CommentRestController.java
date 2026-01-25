package com.mink.trip.comment;

import com.mink.trip.comment.dto.CommentDetail;
import com.mink.trip.comment.service.CommentService;
import com.mink.trip.common.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentRestController {

    private final CommentService commentService;
    @GetMapping("/post/comment/list")
    public List<CommentDetail> getComments(@RequestParam Long postId) {
        return commentService.getCommentList(postId);
    }
    @PostMapping("/post/comment/write")
    public ApiResponse<Void> writeComment(
            @RequestParam long postId,
            @RequestParam String comments,
            @RequestParam(required = false) Long parentId,
            HttpSession session) {

        long userId = (Long) session.getAttribute("userId");

        Map<String, String> result = new HashMap<>();

        if(commentService.createComment(postId, userId, comments, parentId)){
            return ApiResponse.success("댓글 성공");
        }else {
            return ApiResponse.fail("댓글 실패");
        }
    }


}