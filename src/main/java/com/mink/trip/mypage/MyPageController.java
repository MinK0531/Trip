package com.mink.trip.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage")
@Controller
public class MyPageController {

    @GetMapping("/map")

    public String map(){
        return "mypage/map";
    }
}
