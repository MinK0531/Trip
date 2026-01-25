package com.mink.trip.comment.repository;

import com.mink.trip.comment.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPostIdAndIsDeletedFalseOrderByRootIdAscDepthAscIdAsc(Long postId);

}
