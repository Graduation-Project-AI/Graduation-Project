package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Feedback;

@Mapper
public interface FeedbackMapper {
    void saveFeedback(Feedback feedback);
}