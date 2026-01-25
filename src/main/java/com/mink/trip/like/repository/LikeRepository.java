package com.mink.trip.like.repository;

import com.mink.trip.like.domain.Like;
import com.mink.trip.like.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    public int countByPostId(long postId);
    public boolean existsByPostIdAndUserId (long postId, long userId);
    public void deleteByPostId(long postId);

    int countByCommentId(long commentId);
    boolean existsByCommentIdAndUserId(long commentId, long userId);
    void deleteByCommentId(long commentId);


}
