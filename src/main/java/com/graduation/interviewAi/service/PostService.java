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
    
    //게시글 전체 조회
    public List<Post> getAllPosts() {
        return postMapper.selectAllPosts();
    }

    //게시글 작성
    public void createPost(PostRequestDto dto) {
        Post post = new Post();
        post.setPostName(dto.getPostName());
        post.setPContent(dto.getPContent());
        post.setUserId(dto.getUserId());
        post.setUserRole(dto.getUserRole());
        postMapper.insertPost(post);
    }
    
    //특정 게시글 상세 조회 + 해당 게시글의 댓글도 조회
    public PostDetailDto getPostDetail(int postId) {
        PostDetailDto dto = new PostDetailDto();
        dto.setPost(postMapper.getPostById(postId));
        dto.setComments(postMapper.getCommentsByPostId(postId));
        return dto;
    }
    
    //게시글 수정
    public void updatePost(PostRequestDto dto) {
        postMapper.updatePost(dto);
    }

    //게시글 삭제
    public void deletePost(int postId) {
        postMapper.deletePost(postId);
    }


}
