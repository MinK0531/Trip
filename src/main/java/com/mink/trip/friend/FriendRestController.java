package com.mink.trip.friend;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.friend.service.FriendService;
import com.mink.trip.search.dto.UserCard;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendRestController {

    private final FriendService friendService;

    @GetMapping("/list")
    public ApiResponse<List<UserCard>> list(HttpSession session) {
        if (session == null || session.getAttribute("userId") == null) {
            return ApiResponse.fail("로그인이 필요합니다.");
        }

        long userId = (Long) session.getAttribute("userId");

        return ApiResponse.success("친구 목록 조회 성공", friendService.getMyFriends(userId));
    }

}
