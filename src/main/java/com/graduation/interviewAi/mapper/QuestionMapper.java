package com.graduation.interviewAi.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.graduation.interviewAi.domain.Question;
import com.graduation.interviewAi.dto.QuestionDto;

@Mapper
public interface QuestionMapper {
    List<Question> getQuestionsByCategoryAndJob(@Param("category") int category, @Param("jobId") int jobId);
    void insertResumeQuestion(QuestionDto question);
}