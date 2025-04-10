package com.graduation.interviewAi.service;

import com.graduation.interviewAi.domain.Post;
import com.graduation.interviewAi.dto.PostDetailDto;
import com.graduation.interviewAi.dto.PostRequestDto;
import com.graduation.interviewAi.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public List<Post> getAllPosts() {
        return postMapper.selectAllPosts();
    }

    public void createPost(PostRequestDto dto) {
        Post post = new Post();
        post.setPostName(dto.getPostName());
        post.setPContent(dto.getPContent());
        post.setUserId(dto.getUserId());
        post.setUserRole(dto.getUserRole());
        postMapper.insertPost(post);
    }
}
