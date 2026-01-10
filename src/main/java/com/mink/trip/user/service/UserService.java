package com.mink.trip.user.service;

import com.mink.trip.common.SHA256HashingEncoder;
import com.mink.trip.user.domain.User;
import com.mink.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean createUser(
            String signinId,
            String password,
            String name,
            String countryCode,
            String email){

        String encodePassword = SHA256HashingEncoder.encode(password);

        User user = User.builder()
                .signinId(signinId)
                .password(encodePassword)
                .name(name)
                .countryCode(countryCode)
                .email(email)
                .build();
        try {
            userRepository.save(user);
        }catch (DataAccessException e){
            return false;
        }
        return true;
    }

    public boolean isDuplicateId(String signinId) {
        return userRepository.existsBySigninId(signinId);
    }
    public User getUser(String signinId, String password){
        String encodededPassword = SHA256HashingEncoder.encode(password);
        return userRepository.findBySigninIdAndPassword(signinId, encodededPassword);
    }

//    public User getUserById(long id){
//        return userRepository.findById(id);
//    }

}
