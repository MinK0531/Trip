package com.mink.trip.post.service;

import com.mink.trip.comment.dto.CommentDetail;
import com.mink.trip.comment.service.CommentService;
import com.mink.trip.common.FileManager;
import com.mink.trip.country.domain.Country;
import com.mink.trip.country.repository.CountryRepository;
import com.mink.trip.like.service.LikeService;
import com.mink.trip.post.domain.Post;
import com.mink.trip.post.domain.PostImage;
import com.mink.trip.post.dto.PostDetail;
import com.mink.trip.post.dto.PostImageDetail;
import com.mink.trip.post.repository.PostRepository;
import com.mink.trip.user.domain.User;
import com.mink.trip.user.repository.UserRepository;
import com.mink.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private  final LikeService likeService;
    private final CountryRepository countryRepository;
    private final CommentService commentService;

    public boolean createPost(long userId,
                              long countryId,
                              String cityName,
                              String contents,
                              String atmosphere,
                              String placeName,
                              String musicUrl,
                              double latitude,
                              double longitude,
                              List<MultipartFile> images)
    {
        Post post = Post.builder()
                .userId(userId)
                .countryId(countryId)
                .cityName(cityName)
                .contents(contents)
                .atmosphere(atmosphere)
                .placeName(placeName)
                .musicUrl(musicUrl)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        if (images != null && !images.isEmpty()) {
            int order = 0;
            for (MultipartFile image : images) {
                String imagePath = FileManager.saveFile(userId, image);
                if (imagePath == null)
                    return false;

                PostImage postImage = PostImage.builder()
                        .imagePath(imagePath)
                        .sortOrder(order++)
                        .build();

                post.addImage(postImage);
            }
        }

        try {
            postRepository.save(post);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }


    public List<PostDetail> getPostList(long userId) {
        List<Post> postList = postRepository.findAll(Sort.by("id").descending());
        List<PostDetail> postDetailList = new ArrayList<>();

        for(Post post : postList) {
            User user = userService.getUserById(post.getUserId());

            int likeCount = likeService.countByPostId(post.getId());
            boolean isLike = likeService.isLikeByPostIdAndUserId(post.getId(), userId);

            List<PostImageDetail> imageDetails = post.getImages().stream()
                    .map(img -> PostImageDetail.builder()
                            .id(img.getId())
                            .imagePath(img.getImagePath())
                            .sortOrder(img.getSortOrder())
                            .build())
                    .toList();
            String countryName = countryRepository.findById(post.getCountryId())
                    .map(Country::getCountryName)
                    .orElse("알 수 없는 나라");
            List<CommentDetail> commentList = commentService.getCommentList(post.getId());

            PostDetail postDetail = PostDetail.builder()
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

            postDetailList.add(postDetail);
        }
        return postDetailList;
    }
}
