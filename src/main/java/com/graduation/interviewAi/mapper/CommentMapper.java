package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Comment;

@Mapper
public interface CommentMapper {
    void createComment(Comment comment);
}