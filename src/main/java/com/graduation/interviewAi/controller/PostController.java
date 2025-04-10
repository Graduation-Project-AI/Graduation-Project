package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.domain.Post;
import com.graduation.interviewAi.dto.PostDetailDto;
import com.graduation.interviewAi.dto.PostRequestDto;
import com.graduation.interviewAi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
    
    // 게시글 작성
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postDto) {
        postService.createPost(postDto);
        return ResponseEntity.ok("게시글 작성 완료");
    }
    
    // 게시글 상세 조회 + 댓글 목록
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable("postId") int postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }



}