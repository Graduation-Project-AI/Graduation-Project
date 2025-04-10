package com.graduation.interviewAi.mapper;

import com.graduation.interviewAi.domain.Comment;
import com.graduation.interviewAi.domain.Post;
import com.graduation.interviewAi.dto.PostRequestDto;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> selectAllPosts();
    void insertPost(Post post);
    Post getPostById(int postId);
    List<Comment> getCommentsByPostId(int postId);
    void updatePost(PostRequestDto dto);

}
