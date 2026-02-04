package com.mink.trip.post;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.post.dto.PostImageDetail;
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
            @RequestParam List<MultipartFile> images,
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
    @GetMapping("/images")
    public ApiResponse<List<PostImageDetail>> getImages(
            @RequestParam long postId,
            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");

        List<PostImageDetail> images = postService.getPostImageList(userId, postId);

        return ApiResponse.success("이미지 조회 성공", images);
    }


    @PutMapping("/update")
    public ApiResponse<Void> updatePost(
            @RequestParam long postId,
            @RequestParam long countryId,
            @RequestParam String cityName,
            @RequestParam String contents,
            @RequestParam String atmosphere,
            @RequestParam String placeName,
            @RequestParam(required = false) String musicUrl,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(required = false) List<MultipartFile> imageFiles,

            @RequestParam(required = false) List<Long> removeImageIds,

            HttpSession session
    ) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");

        if (postService.updatePost(
                userId, postId,
                countryId, cityName, contents, atmosphere, placeName,
                musicUrl, latitude, longitude,
                imageFiles,
                removeImageIds
        )) {
            return ApiResponse.success("수정 성공");
        } else {
            return ApiResponse.fail("수정 실패");
        }
    }



    @DeleteMapping("/remove")
    public ApiResponse<Void> removePost(
            @RequestParam long postId
            , HttpSession session) {

        long userId = (Long)session.getAttribute("userId");

        if(postService.deletePost(userId, postId)) {
            return ApiResponse.success("삭제 성공");
        } else {
            return ApiResponse.fail("삭제 실패");
        }

    }
//    @GetMapping("/detail")
//    public ApiResponse<PostDetail> detail(
//            @RequestParam long postId,
//            HttpSession session
//    ) {
//        if (session == null || session.getAttribute("userId") == null) {
//            return ApiResponse.fail("로그인이 필요합니다.");
//        }
//        long userId = (Long) session.getAttribute("userId");
//
//        PostDetail detail = postService.getPostDetail(userId, postId);
//        if (detail == null) {
//            return ApiResponse.fail("게시물을 찾을 수 없습니다.");
//        }
//        return ApiResponse.success("게시물 상세 조회 성공", detail);
//    }


}

