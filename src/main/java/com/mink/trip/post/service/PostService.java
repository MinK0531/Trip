package com.mink.trip.post.service;

import com.mink.trip.common.FileManager;
import com.mink.trip.post.domain.Post;
import com.mink.trip.post.domain.PostImage;
import com.mink.trip.post.repository.PostRepository;
import com.mink.trip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

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



    public List<Post> getPostList() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
