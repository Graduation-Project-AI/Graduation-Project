package com.graduation.interviewAi.service;

import com.graduation.interviewAi.domain.Comment;
import com.graduation.interviewAi.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    public void saveComment(Comment commentRequest) {
        if (commentRequest.getCContent() == null || commentRequest.getCContent().isBlank()) {
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
        }

        Comment comment = new Comment();

        comment.setPostId(commentRequest.getPostId());
        comment.setCContent(commentRequest.getCContent());
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        comment.setUserId(commentRequest.getUserId());
        comment.setUserRole(commentRequest.getUserRole());

        commentMapper.insertComment(comment);
    }

    public boolean deleteCommentById(int commentId) {
        int deletedCount = commentMapper.deleteCommentById(commentId);
        return deletedCount > 0;
    }

}
