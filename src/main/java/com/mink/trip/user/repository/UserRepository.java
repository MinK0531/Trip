package com.mink.trip.user.repository;

import com.mink.trip.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsBynickName(String nickName);
    public boolean existsByEmail(String email);
    public User findByEmailAndPassword(String email, String password);
    public List<User> findByNickNameContainingIgnoreCaseOrderByIdDesc(String nickName);

}
