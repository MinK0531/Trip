package com.mink.trip.mypage;

import com.mink.trip.post.repository.PostRepository;
import com.mink.trip.post.service.PostService;
import com.mink.trip.wishlist.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Controller
public class MyPageController {

    private final PostRepository postRepository;
    private final WishlistRepository wishlistRepository;

    @GetMapping("/map")

    public String map(HttpSession session, Model model){
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        int postCount = postRepository.countByUserId(userId);
        int wishlistCount = wishlistRepository.countByUserId(userId);

        model.addAttribute("postCount", postCount);
        model.addAttribute("wishlistCount", wishlistCount);

        return "/mypage/map";

    }

}
