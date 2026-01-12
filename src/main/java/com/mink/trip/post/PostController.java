package com.mink.trip.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/post")
@Controller
public class PostController {

    @GetMapping("/timeline")
    public String timeline(){
        return "post/timeline";
    }
    @GetMapping("/map")
    public String map(){
        return "mypage/map";
    }
}
