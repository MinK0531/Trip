package com.mink.trip.user;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.user.domain.User;
import com.mink.trip.user.repository.UserRepository;
import com.mink.trip.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserRestController {

    private final UserService userService;

    @PostMapping("/signup-process")
    public ApiResponse<Void> signup(

            @RequestParam String nickName
            ,@RequestParam String password
            ,@RequestParam String name
            ,@RequestParam String countryCode
            ,@RequestParam String email)
    {

        if(userService.createUser(nickName, password, name, countryCode, email)){
            return ApiResponse.success("회원가입 성공");
        }else{
            return ApiResponse.fail("회원가입 실패");
        }

    }
    @GetMapping("/duplicate-nickName")
    public ApiResponse<Boolean> isDuplicateNickName(@RequestParam String nickName){

        if(userService.isDuplicateNickName(nickName)){
            return ApiResponse.success("중복된 닉네임", true);
        }else{
            return ApiResponse.success("사용 가능한 닉네임", false);
        }
    }
    @GetMapping("/duplicate-email")
    public ApiResponse<Boolean> isDuplicateEmail(@RequestParam String email){

        if(userService.isDuplicateEmail(email)){
            return ApiResponse.success("중복된 이메일", true);
        }else{
            return ApiResponse.success("사용 가능한 이메일", false);
        }
    }


    @PostMapping("/signin-process")
    public ApiResponse<Void> signin(
            @RequestParam String email
            , @RequestParam String password
            , HttpServletRequest request){
        User user = userService.getUser(email,password);

        if(user != null){
            HttpSession session = request.getSession();

            session.setAttribute("userId",user.getId());
            session.setAttribute("userNickName",user.getNickName());
            session.setAttribute("userCountry", user.getCountryCode());

            return ApiResponse.success("로그인 성공");
        }else{
            return ApiResponse.fail("로그인 실패");
        }
    }




}
