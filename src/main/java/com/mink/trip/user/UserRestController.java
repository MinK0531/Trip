package com.mink.trip.user;

import com.mink.trip.common.dto.ApiResponse;
import com.mink.trip.user.repository.UserRepository;
import com.mink.trip.user.service.UserService;
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
            ,@RequestParam String email)
    {

        Map<String,String> resultMap = new HashMap<>();

        if(userService.createUser(signinId, password, name, email)){
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

}
