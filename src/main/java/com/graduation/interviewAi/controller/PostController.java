package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.domain.Post;
import com.graduation.interviewAi.dto.PostDetailDto;
import com.graduation.interviewAi.dto.PostRequestDto;
import com.graduation.interviewAi.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name="PostController", description="커뮤니티 API")
public class PostController {
    private final PostService postService;

    // 게시글 목록 조회
    @GetMapping
    @Operation(summary = "게시글 목록 조회")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
    
    // 게시글 작성
    @PostMapping
    @Operation(summary = "게시글 작성")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postDto) {
        postService.createPost(postDto);
        return ResponseEntity.ok("게시글 작성 완료");
    }
    
    // 게시글 상세 조회 + 댓글 목록
    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable("postId") int postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<String> updatePost(@PathVariable("postId") int postId,
                                             @RequestBody PostRequestDto dto) {
        dto.setPostId(postId);
        postService.updatePost(dto);
        return ResponseEntity.ok("게시글이 수정되었습니다.");
    }

    // 게시글 삭제
    @PostMapping("/{postId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<String> deletePost(@PathVariable("postId") int postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }




}