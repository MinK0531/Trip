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
    public Map<String,String> signup(
            @RequestParam String signinId
            ,@RequestParam String password
            ,@RequestParam String name
            ,@RequestParam String countryCode
            ,@RequestParam String email)
    {

        Map<String,String> resultMap = new HashMap<>();

        if(userService.createUser(signinId, password, name, countryCode, email)){
            resultMap.put("result","success");
        }else{
            resultMap.put("result","fail");
        }
        return resultMap;

    }
    @GetMapping("/duplicate-id")
    public Map<String, Boolean> isDuplicateId(@RequestParam String signinId){

        Map<String,Boolean> resultMap = new HashMap<>();

        if(userService.isDuplicateId(signinId)){
            resultMap.put("isDuplicate",true);
        }else{
            resultMap.put("isDuplicate",false);
        }
        return  resultMap;
    }

    @PostMapping("/signin-process")
    public Map<String, String> signin(
            @RequestParam String signinId
            , @RequestParam String password
            , HttpServletRequest request){
        User user = userService.getUser(signinId,password);

        Map<String,String> resultMap = new HashMap<>();

        if(user != null){
            resultMap.put("result", "success");
            HttpSession session = request.getSession();


            session.setAttribute("userId",user.getId());
            session.setAttribute("userSigninId",user.getSigninId());

        }else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

}
