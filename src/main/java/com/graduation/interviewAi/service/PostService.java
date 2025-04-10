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


}
