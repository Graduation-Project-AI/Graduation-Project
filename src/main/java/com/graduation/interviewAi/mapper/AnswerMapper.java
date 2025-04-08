package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Answer;
import com.graduation.interviewAi.dto.AnswerWithQuestionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnswerMapper {
    void saveAnswer(Answer answer);
    List<AnswerWithQuestionDto> findAnswersByInterviewId(int interviewId);
    List<String> findAnswersByQuestionId(int questionId, int interviewId);
    void insertEmptyAnswer(@Param("questionId") int questionId, @Param("interviewId") int interviewId);
    Answer findByInterviewIdAndQuestionId(@Param("interviewId") int interviewId, @Param("questionId") int questionId);
    void updateAcontentByInterviewAndQuestion(@Param("interviewId") int interviewId, @Param("questionId") int questionId, @Param("acontent") String acontent);

}