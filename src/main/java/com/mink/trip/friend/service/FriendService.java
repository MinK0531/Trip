package com.mink.trip.friend.service;

import com.mink.trip.friend.domain.Friend;
import com.mink.trip.friend.repository.FriendRepository;
import com.mink.trip.user.domain.UserProfile;
import com.mink.trip.user.repository.UserProfileRepository;
import com.mink.trip.user.repository.UserRepository;
import com.mink.trip.search.dto.UserCard;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public List<UserCard> getMyFriends(long userId) {
        return friendRepository.findByUserIdOrderByIdDesc(userId).stream()
                .map(f -> {
                    var u = userRepository.findById(f.getFriendUserId()).orElse(null);
                    if (u == null) return null;

                    UserProfile up = userProfileRepository.findByUserId(u.getId());
                    String profileImg = (up != null) ? up.getProfileImg() : null;
                    String profileWord = (up != null) ? up.getProfileWord() : null;

                    return UserCard.builder()
                            .userId(u.getId())
                            .nickName(u.getNickName())
                            .profileImg(profileImg)
                            .profileWord(profileWord)
                            .build();
                })
                .filter(x -> x != null)
                .toList();
    }

}
