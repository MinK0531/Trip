package com.mink.trip.search;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.search.domain.Search;
import com.mink.trip.search.service.SearchService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchRestController {

    private final SearchService searchService;

    @GetMapping
    public ApiResponse<Search> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "all") String tab,
            HttpSession session
    ) {
        Long userId = getLoginUserId(session);
        if (userId == null) return ApiResponse.fail("로그인이 필요하다");

        Search data = searchService.search(userId, keyword, tab);
        return ApiResponse.success("검색 성공", data);
    }

    private Long getLoginUserId(HttpSession session) {
        if (session == null) return null;
        Object v = session.getAttribute("userId");
        if (v == null) return null;
        return (Long) v;
    }
}
