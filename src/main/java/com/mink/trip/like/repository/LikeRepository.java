package com.mink.trip.like.repository;

import com.mink.trip.like.domain.Like;
import com.mink.trip.like.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    public int countByPostIdAndCommentId(long postId, long commentId);
    public boolean existsByPostIdAndUserIdAndCommentId (long postId, long userId, long commentId);
    void deleteByPostId(long postId);
    public int countByCommentIdAndPostId(long commentId, long postId);
    public boolean existsByCommentIdAndUserIdAndPostId(long commentId, long userId, long postId);
    public void deleteByCommentIdAndUserIdAndPostId(long commentId, long userId, long postId);

}
