package com.mink.trip.user.service;

import com.mink.trip.common.SHA256HashingEncoder;
import com.mink.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean createUser(
            String signinId,
            String password,
            String name,
            String email){

        String encodePassword = SHA256HashingEncoder.encode(password);

        int count = userRepository.insertUser(signinId, encodePassword, name, email);
        if(count == 1){
            return true;
        }else{
            return false;
        }
    }
    public boolean isDuplicateId(String signinId){
        int count = userRepository.countBySigninId(signinId);
        if(count == 0){
            return false;
        }else {
            return true;
        }
    }

}
