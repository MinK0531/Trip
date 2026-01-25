package com.mink.trip.comment;

import com.mink.trip.comment.dto.CommentDetail;
import com.mink.trip.comment.service.CommentService;
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
    public Map<String, String> writeComment(
            @RequestParam Long postId,
            @RequestParam String comments,
            @RequestParam(required = false) Long parentId,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        Map<String, String> result = new HashMap<>();
        boolean success = commentService.createComment(postId, userId, comments, parentId);

        result.put("result", success ? "success" : "fail");
        return result;
    }

}