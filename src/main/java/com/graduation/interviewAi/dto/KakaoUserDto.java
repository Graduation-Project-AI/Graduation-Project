package com.graduation.interviewAi.dto;
import lombok.Data;

@Data
public class KakaoUserDto {
    private Integer userId; // DB userId
    private String name;
    private String email;
}