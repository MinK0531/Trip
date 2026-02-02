package com.mink.trip.search.service;

import com.mink.trip.friend.repository.FriendRepository;
import com.mink.trip.friend.service.FriendService;
import com.mink.trip.post.dto.CountryCityRow;
import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.post.service.PostService;
import com.mink.trip.post.repository.PostRepository;
import com.mink.trip.search.dto.CountryCard;
import com.mink.trip.search.domain.Search;
import com.mink.trip.search.dto.UserCard;
import com.mink.trip.user.domain.User;
import com.mink.trip.user.domain.UserProfile;
import com.mink.trip.user.repository.UserProfileRepository;
import com.mink.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final FriendService friendService;
    private final FriendRepository friendRepository;

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    private final PostService postService;
    private final PostRepository postRepository;

    public Search search(long loginUserId, String keyword, String tab) {
        String k = (keyword == null) ? "" : keyword.trim();
        if (k.isEmpty()) {
            return Search.builder()
                    .friends(List.of())
                    .users(List.of())
                    .countries(List.of())
                    .posts(List.of())
                    .build();
        }

        List<UserCard> myFriends = List.of();
        if ("all".equals(tab) || "users".equals(tab)) {
            myFriends = friendService.getMyFriends(loginUserId);
        }

        Set<Long> friendIdSet = new HashSet<>();
        if (!myFriends.isEmpty()) {
            for (UserCard c : myFriends) friendIdSet.add(c.getUserId());
        }

        List<UserCard> users = List.of();
        if ("all".equals(tab) || "users".equals(tab)) {
            List<User> found = userRepository.findByNickNameContainingIgnoreCaseOrderByIdDesc(k);
            users = found.stream()
                    .filter(u -> u.getId() != loginUserId)
                    .map(u -> {
                        UserProfile up = userProfileRepository.findByUserId(u.getId());
                        String img = (up != null) ? up.getProfileImg() : null;
                        String word = (up != null) ? up.getProfileWord() : null;
                        return UserCard.builder()
                                .userId(u.getId())
                                .nickName(u.getNickName())
                                .profileImg(img)
                                .profileWord(word)
                                .build();
                    })
                    .toList();
        }

        List<CountryCard> countries = List.of();
        if ("all".equals(tab) || "countries".equals(tab)) {
            List<CountryCityRow> rows = postRepository.searchCountryCity(k);

            Map<Long, String> countryNameMap = new LinkedHashMap<>();
            Map<Long, Set<String>> cityMap = new LinkedHashMap<>();

            for (CountryCityRow r : rows) {
                countryNameMap.putIfAbsent(r.getCountryId(), r.getCountryName());
                cityMap.putIfAbsent(r.getCountryId(), new LinkedHashSet<>());
                if (r.getCityName() != null && !r.getCityName().isBlank()) {
                    cityMap.get(r.getCountryId()).add(r.getCityName());
                }
            }

            countries = countryNameMap.keySet().stream()
                    .map(countryId -> CountryCard.builder()
                            .countryId(countryId)
                            .countryName(countryNameMap.get(countryId))
                            .cities(cityMap.getOrDefault(countryId, Set.of()).stream().toList())
                            .build())
                    .toList();
        }

        List<PostDetail> posts = List.of();
        if ("all".equals(tab) || "countries".equals(tab)) {
            posts = postService.searchPosts(loginUserId, k);
        }

        return Search.builder()
                .friends(myFriends)
                .users(users)
                .countries(countries)
                .posts(posts)
                .build();
    }
}
