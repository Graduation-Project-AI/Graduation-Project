package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.graduation.interviewAi.domain.Answer;

@Mapper
public interface AnswerMapper {
    void insertEmptyAnswer(@Param("questionId") int questionId, @Param("interviewId") int interviewId);
    Answer findByInterviewIdAndQuestionId(@Param("interviewId") int interviewId, @Param("questionId") int questionId);
    void updateAcontentByInterviewAndQuestion(@Param("interviewId") int interviewId, @Param("questionId") int questionId, @Param("acontent") String acontent);

}