package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Answer;

@Mapper
public interface AnswerMapper {
    void saveAnswer(Answer answer);
}