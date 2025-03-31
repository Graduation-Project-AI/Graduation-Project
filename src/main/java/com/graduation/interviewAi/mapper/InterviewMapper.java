package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Interview;

@Mapper
public interface InterviewMapper {
    void insertInterview(Interview interview);
    Interview findInterviewById(Integer interviewId);
}