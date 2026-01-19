package com.mink.trip.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/search")
@Controller
@RequiredArgsConstructor
public class SearchController {

    @GetMapping("/timeline")
    public String search(){
        return "/search/timeline";
    }
}
