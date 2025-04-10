package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Comment;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    void insertComment(Comment comment);
    int deleteCommentById(@Param("commentId") int commentId);
}