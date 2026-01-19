package com.mink.trip.post;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @PostMapping("/write")
    public ApiResponse<Void> write(
            @RequestParam long countryId,
            @RequestParam(required = false) String cityName,
            @RequestParam String contents,
            @RequestParam(required = false) String atmosphere,
            @RequestParam(required = false) String placeName,
            @RequestParam(required = false) String musicUrl,
            @RequestParam(required = false, defaultValue = "0.0") double latitude,
            @RequestParam(required = false, defaultValue = "0.0") double longitude,
            @RequestParam(required = false) List<MultipartFile> images,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (long) session.getAttribute("userId");

        if(postService.createPost(
                userId,
                countryId,
                cityName,
                contents,
                atmosphere,
                placeName,
                musicUrl,
                latitude,
                longitude,
                images)){
            return ApiResponse.success("게시물 등록 성공");
        } else {
            return ApiResponse.fail("게시물 등록 실패");
        }
    }

}


