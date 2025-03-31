package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Post;

@Mapper
public interface PostMapper {
    void createPost(Post post);
    Post getPostById(Integer postId);
}