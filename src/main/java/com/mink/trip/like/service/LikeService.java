package com.mink.trip.like.service;

import com.mink.trip.like.domain.Like;
import com.mink.trip.like.domain.LikeId;
import com.mink.trip.like.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public boolean createLikePost(long postId, long userId){

        Like like = Like.builder()
                .postId(postId)
                .commentId(0L)
                .userId(userId)
                .build();
        try {
            likeRepository.save(like);
        }catch (DataAccessException e){
            return false;
        }
        return true;
    }
    public boolean createLikeComment(long commentId, long userId) {
        Like like = Like.builder()
                .postId(0L)
                .commentId(commentId)
                .userId(userId)
                .build();

        try {
            likeRepository.save(like);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    public int countByPostId(long postId){
        return likeRepository.countByPostIdAndCommentId(postId, 0L);
    }
    public boolean isLikeByPostIdAndUserId(long postId, long userId){
        return likeRepository.existsByPostIdAndUserIdAndCommentId(postId, userId, 0L);
    }
    public int countLikeByComment(long commentId) {
        return likeRepository.countByCommentIdAndPostId(commentId, 0L);
    }

    public boolean isLikeComment(long commentId, long userId) {
        return likeRepository.existsByCommentIdAndUserIdAndPostId(commentId, userId, 0L);
    }


    public boolean deleteLikePost(long postId, long userId) {

        LikeId likeId = LikeId.builder()
                .postId(postId)
                .commentId(0L)
                .userId(userId)
                .build();

        Optional<Like> optionalLike = likeRepository.findById(likeId);

        if(optionalLike.isPresent()) {

            try {
                likeRepository.delete(optionalLike.get());
            } catch(DataAccessException e) {
                return false;
            }


        } else {
            return false;
        }

        return true;
    }
    public boolean deleteLikeComment(long commentId, long userId) {
        LikeId likeId = LikeId.builder()
                .postId(0L)
                .commentId(commentId)
                .userId(userId)
                .build();

        Optional<Like> optionalLike = likeRepository.findById(likeId);
        if(optionalLike.isPresent()) {
            try {
                likeRepository.delete(optionalLike.get());
            } catch(DataAccessException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    @Transactional
    public void deleteLikeByPostId(long postId) {
        likeRepository.deleteByPostId(postId);
    }

}
