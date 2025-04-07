package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Answer;
import com.graduation.interviewAi.dto.AnswerWithQuestionDto;

import java.util.List;

@Mapper
public interface AnswerMapper {
    void saveAnswer(Answer answer);
    List<AnswerWithQuestionDto> findAnswersByInterviewId(int interviewId);
    List<String> findAnswersByQuestionId(int questionId, int interviewId);

}