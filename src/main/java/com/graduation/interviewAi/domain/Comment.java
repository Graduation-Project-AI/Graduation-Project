package com.graduation.interviewAi.domain;
import java.sql.Timestamp;

import lombok.Data;
@Data
public class Comment {
    private Integer commentId;
    private String commentName;
    private String cContent;
    private Timestamp createdAt;
    private Integer userId;
}