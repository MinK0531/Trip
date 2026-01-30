package com.mink.trip.post;

import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/detail_popup")
    public String detail_popup(Model model
            ,HttpSession session) {
        long userId = (Long) session.getAttribute("userId");
        List<PostDetail> postList = postService.getPostList(userId);

        model.addAttribute("postList", postList);

        return "/post/popup_detail";
    }

    @GetMapping("/create")
    public String map(){
        return "/layouts/create";
    }
}
