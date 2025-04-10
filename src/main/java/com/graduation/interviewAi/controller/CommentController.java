package com.graduation.interviewAi.controller;

import com.graduation.interviewAi.domain.Comment;
import com.graduation.interviewAi.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="CommentController", description="커뮤니티 게시글 댓글 API")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/posts/{postId}/comments")
    @Operation(summary = "댓글 작성")
    public ResponseEntity<String> addComment(@PathVariable Integer postId, @RequestBody Comment comment) {
        comment.setPostId(postId); // URL에서 postId 받아와서 설정
        commentService.saveComment(comment);
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }

    // 댓글 삭제
    @PostMapping("/api/comments/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }


}
