package com.graduation.interviewAi.dto;

import lombok.Data;

@Data
public class PostRequestDto {
    private String postName;
    private String pContent;
    private Integer userId;
}
