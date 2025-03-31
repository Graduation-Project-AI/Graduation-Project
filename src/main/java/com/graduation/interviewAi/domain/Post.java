package com.graduation.interviewAi.domain;
import java.sql.Timestamp;

import lombok.Data;
@Data
public class Post {
    private Integer postId;
    private String postName;
    private String pContent;
    private Timestamp createdAt;
    private Integer userId;
}