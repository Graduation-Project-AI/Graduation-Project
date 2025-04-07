package com.graduation.interviewAi.service;

import com.graduation.interviewAi.dto.AnswerWithQuestionDto;
import com.graduation.interviewAi.mapper.AnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerMapper answerMapper;

    public List<AnswerWithQuestionDto> getAnswersByInterviewId(int interviewId) {
        return answerMapper.findAnswersByInterviewId(interviewId);
    }
}
