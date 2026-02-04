package com.mink.trip.post;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post_card")
    public String detail_popup(Model model, HttpSession session) {
        long userId = (Long) session.getAttribute("userId");

        List<PostDetail> postList = postService.getPostList(userId);
        model.addAttribute("postList", postList);
        return "/post/post_card";
    }

    @GetMapping("/detail")
    @ResponseBody
    public ApiResponse<PostDetail> detail(
            @RequestParam("postId") long postId,
            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }
        long userId = (Long) session.getAttribute("userId");

        PostDetail detail = postService.getPostDetail(userId, postId);
        if (detail == null) {
            return ApiResponse.fail("게시물을 찾을 수 없습니다.");
        }
        return ApiResponse.success("게시물 상세 조회 성공", detail);
    }
    @GetMapping("/create")
    public String create(Model model, HttpSession session) {
        long userId = (Long) session.getAttribute("userId");

        List<PostDetail> postList = postService.getPostList(userId);
        model.addAttribute("postList", postList);

        return "/layouts/create";
    }

}
