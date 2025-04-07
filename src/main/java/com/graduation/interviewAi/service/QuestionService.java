package com.graduation.interviewAi.service;

import com.graduation.interviewAi.dto.QuestionDto;
import com.graduation.interviewAi.mapper.QuestionMapper;
import com.graduation.interviewAi.mapper.AnswerMapper;
import com.graduation.interviewAi.mapper.InterviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionMapper questionMapper;
    private final InterviewMapper interviewMapper;
    private final AnswerMapper answerMapper;

    public List<QuestionDto> getQuestions(int interviewId, int count) {
        if (count < 3) {
            throw new IllegalArgumentException("질문은 최소 3개 이상 요청해야 합니다.");
        }

        // Interview에서 jobId 가져오기
        Integer jobId = interviewMapper.getJobIdByInterviewId(interviewId);

        // 1. 시작 질문 (category 1) ==> (category, count, jobId, interiviewSourceId)
        List<QuestionDto> start = questionMapper.getQuestionsByCategory(1, 1, jobId, null);

        // 2. 끝 질문 (category 4)
        List<QuestionDto> end = questionMapper.getQuestionsByCategory(4, 1, jobId, null);

        // 남은 개수 계산
        int remaining = count - 2;
        int half = remaining / 2;
        int rest = remaining - half;

        // 3. 직군 질문 (category 2)
        List<QuestionDto> jobQuestions = questionMapper.getQuestionsByCategory(2, half, jobId, null);

        // 4. 자소서 기반 질문 (category 3)
        List<QuestionDto> resumeQuestions = questionMapper.getQuestionsByCategory(3, rest, jobId, interviewId);

        // 전부 합치기
        List<QuestionDto> result = new ArrayList<>();
        result.addAll(start);
        result.addAll(jobQuestions);
        result.addAll(resumeQuestions);
        result.addAll(end);
        
        //Answer table 에 질문 넣어두기
        for (QuestionDto q : result) {
            answerMapper.insertEmptyAnswer(q.getQuestionId(), interviewId);
        }
        return result;

    }
}
