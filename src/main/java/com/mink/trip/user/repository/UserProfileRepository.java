package com.mink.trip.user.repository;

import com.mink.trip.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    public UserProfile findByUserId(long userId);

}
