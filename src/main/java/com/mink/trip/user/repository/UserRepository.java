package com.mink.trip.user.repository;

import com.mink.trip.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsBySigninId(String signinId);

    public User findBySigninIdAndPassword(String signinId, String password);

}
