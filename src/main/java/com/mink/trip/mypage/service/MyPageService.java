package com.mink.trip.mypage.service;

import com.mink.trip.comment.dto.CommentDetail;
import com.mink.trip.comment.service.CommentService;
import com.mink.trip.country.domain.Country;
import com.mink.trip.country.repository.CountryRepository;
import com.mink.trip.like.service.LikeService;
import com.mink.trip.mypage.dto.MyPagePostPoint;
import com.mink.trip.post.domain.Post;
import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.post.dto.PostImageDetail;
import com.mink.trip.post.repository.PostRepository;
import com.mink.trip.user.domain.User;
import com.mink.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeService likeService;
    private final CountryRepository countryRepository;
    private final CommentService commentService;

    public List<MyPagePostPoint> getMyPostPoints(long userId) {
        List<Post> posts = postRepository.findByUserIdOrderByIdDesc(userId);

        return posts.stream()
                .filter(p -> !(p.getLatitude() == 0.0 && p.getLongitude() == 0.0))
                .map(p -> MyPagePostPoint.builder()
                        .postId(p.getId())
                        .latitude(p.getLatitude())
                        .longitude(p.getLongitude())
                        .build())
                .toList();
    }


    public PostDetail getMyPostDetail(long userId, long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) return null;

        Post post = optionalPost.get();
        if (post.getUserId() != userId) return null; // 내 글만

        User user = userService.getUserById(post.getUserId());

        int likeCount = likeService.countByPostId(post.getId());
        boolean isLike = likeService.isLikeByPostIdAndUserId(post.getId(), userId);

        String countryName = countryRepository.findById(post.getCountryId())
                .map(Country::getCountryName)
                .orElse("알 수 없는 나라");

        List<CommentDetail> commentList = commentService.getCommentList(post.getId(),userId);

        List<PostImageDetail> imageDetails = post.getImages().stream()
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .map(img -> PostImageDetail.builder()
                        .id(img.getId())
                        .imagePath(img.getImagePath())
                        .sortOrder(img.getSortOrder())
                        .build())
                .toList();

        return PostDetail.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .nickName(user.getNickName())
                .countryId(post.getCountryId())
                .countryName(countryName)
                .cityName(post.getCityName())
                .contents(post.getContents())
                .atmosphere(post.getAtmosphere())
                .placeName(post.getPlaceName())
                .musicUrl(post.getMusicUrl())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .likeCount(likeCount)
                .isLike(isLike)
                .commentCount(commentList.size())
                .commentList(commentList)
                .createdAt(post.getCreatedAt())
                .imageList(imageDetails)
                .build();
    }
}
