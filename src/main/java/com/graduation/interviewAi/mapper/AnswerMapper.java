package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.graduation.interviewAi.domain.Answer;
import com.graduation.interviewAi.dto.AnswerWithQuestionDto;

import java.util.List;

@Mapper
public interface AnswerMapper {
    List<AnswerWithQuestionDto> findAnswersByInterviewId(int interviewId);
    void insertEmptyAnswer(@Param("questionId") int questionId, @Param("interviewId") int interviewId);

}