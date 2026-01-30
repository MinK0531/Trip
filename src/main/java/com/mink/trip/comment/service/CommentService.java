package com.mink.trip.comment.service;

import com.mink.trip.comment.domain.Comment;
import com.mink.trip.comment.dto.CommentDetail;
import com.mink.trip.comment.repository.CommentRepository;
import com.mink.trip.like.service.LikeService;
import com.mink.trip.user.domain.User;
import com.mink.trip.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final LikeService likeService;
    public List<CommentDetail> getCommentList(Long postId, Long userId) {
        List<Comment> comments =
                commentRepository.findByPostIdAndIsDeletedFalseOrderByRootIdAscDepthAscIdAsc(postId);

        List<CommentDetail> result = new ArrayList<>();

        for (Comment comment : comments) {
            User user = userService.getUserById(comment.getUserId());
            int likeCount = likeService.countLikeByComment(comment.getId());
            boolean isLike = likeService.isLikeComment(comment.getId(), userId);

            result.add(CommentDetail.builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .nickName(user.getNickName())
                    .comments(comment.getComment())
                    .parentId(comment.getParentId())
                    .rootId(comment.getRootId())
                    .depth(comment.getDepth())
                    .isDeleted(comment.isDeleted())
                    .likeCount(likeCount)
                    .isLike(isLike)
                    .build());
        }
        return result;
    }
    public boolean createComment(Long postId, Long userId, String comments, Long parentId) {
        try {
            if (parentId == null) {
                Comment comment = Comment.builder()
                        .postId(postId)
                        .userId(userId)
                        .parentId(null)
                        .rootId(0L)
                        .depth(0)
                        .isDeleted(false)
                        .comment(comments)
                        .build();

                Comment saved = commentRepository.save(comment);

                Comment updateRoot = Comment.builder()
                        .id(saved.getId())
                        .postId(saved.getPostId())
                        .userId(saved.getUserId())
                        .parentId(null)
                        .rootId(saved.getId())
                        .depth(0)
                        .isDeleted(false)
                        .comment(saved.getComment())
                        .build();

                commentRepository.save(updateRoot);
            } else {
                Comment parent = commentRepository.findById(parentId)
                        .orElseThrow(() -> new RuntimeException("부모 댓글이 없습니다."));

                Comment reply = Comment.builder()
                        .postId(postId)
                        .userId(userId)
                        .parentId(parentId)
                        .rootId(parent.getRootId())
                        .depth(parent.getDepth() + 1)
                        .isDeleted(false)
                        .comment(comments)
                        .build();

                commentRepository.save(reply);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Transactional
    public void deleteCommentByPostId(long postId) {
        commentRepository.deleteByPostId(postId);
    }
}