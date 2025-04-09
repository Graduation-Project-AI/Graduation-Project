package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Interview;

import java.util.List;

@Mapper
public interface InterviewMapper {
    void insertInterview(Interview interview);
    Interview findInterviewById(Integer interviewId);
    Integer getResumeIdByInterviewId(Integer interviewId);
    Integer getJobIdByInterviewId(Integer interviewId);
    List<Interview> findInterviewsByUserId(Integer interviewId);
}