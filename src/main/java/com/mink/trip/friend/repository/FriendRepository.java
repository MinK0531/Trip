package com.mink.trip.friend.repository;

import com.mink.trip.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    public List<Friend> findByUserIdOrderByIdDesc(long userId);


}
