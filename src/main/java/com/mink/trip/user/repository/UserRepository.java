package com.mink.trip.user.repository;

import com.mink.trip.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsBySigninId(String signinId);

    public User findBySigninIdAndPassword(String signinId, String password);

    public User findById(long id);
}
