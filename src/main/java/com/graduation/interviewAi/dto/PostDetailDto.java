package com.graduation.interviewAi.dto;

import com.graduation.interviewAi.domain.Comment;
import com.graduation.interviewAi.domain.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostDetailDto {
	// post 상세정보를 조회할때 (post 1개 + 댓글 목록)
    private Post post;
    private List<Comment> comments;
}
