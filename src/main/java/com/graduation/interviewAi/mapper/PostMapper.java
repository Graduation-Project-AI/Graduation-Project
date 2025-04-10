package com.graduation.interviewAi.mapper;

import com.graduation.interviewAi.domain.Comment;
import com.graduation.interviewAi.domain.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> selectAllPosts();
}
