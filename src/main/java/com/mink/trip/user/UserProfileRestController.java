package com.mink.trip.user;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.user.dto.UserProfileDetail;
import com.mink.trip.user.service.UserProfileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileRestController {

    private final UserProfileService userProfileService;

    @GetMapping
    public ApiResponse<UserProfileDetail> getMyProfile(HttpSession session) {
        Long userId = getLoginUserId(session);
        if (userId == null) {
            return ApiResponse.fail("로그인이 필요하다");
        }

        UserProfileDetail data = userProfileService.getMyProfile(userId);
        return ApiResponse.success("프로필 조회 성공", data);
    }

    @PutMapping("/save")
    public ApiResponse<UserProfileDetail> upsertMyProfile(
            @RequestParam(required = false) String profileWord,
            @RequestParam(required = false) MultipartFile profileImg,
            HttpSession session
    ) {
        Long userId = getLoginUserId(session);
        if (userId == null) {
            return ApiResponse.fail("로그인이 필요하다");
        }

        UserProfileDetail detail =
                userProfileService.upsert(userId, profileWord, profileImg);

        if (detail == null) {
            return ApiResponse.fail("프로필 저장 실패");
        }
        session.setAttribute("userProfileImg", detail.getProfileImg());
        session.setAttribute("userProfileWord", detail.getProfileWord());
        return ApiResponse.success("프로필 저장 성공", detail);
    }

    private Long getLoginUserId(HttpSession session) {
        if (session == null) return null;
        Object v = session.getAttribute("userId");
        if (v == null) return null;
        return (Long) v;
    }
    @DeleteMapping("/delete")
    public ApiResponse<UserProfileDetail> deleteProfileImage(HttpSession session) {
        Long userId = getLoginUserId(session);
        if (userId == null) return ApiResponse.fail("로그인이 필요하다");

        UserProfileDetail detail = userProfileService.deleteMyProfileImage(userId);
        if (detail == null) return ApiResponse.fail("프로필 이미지 삭제 실패");

        session.setAttribute("userProfileImg", null);
        session.setAttribute("userProfileWord", detail.getProfileWord());

        return ApiResponse.success("프로필 이미지 삭제 성공", detail);
    }



}
