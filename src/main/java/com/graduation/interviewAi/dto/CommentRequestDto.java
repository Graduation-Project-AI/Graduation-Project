package com.graduation.interviewAi.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String commentName;
    private String cContent;
    private Integer userId;
}