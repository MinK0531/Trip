package com.mink.trip.user.repository;

import com.mink.trip.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRepository {
    public int insertUser(
            @Param("signinId") String signinId,
            @Param("password") String password,
            @Param("name") String name,
            @Param("email") String email);

    public int countBySigninId(@Param("signinId") String signinId);


}
