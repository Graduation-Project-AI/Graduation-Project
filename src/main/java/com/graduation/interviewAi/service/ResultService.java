package com.graduation.interviewAi.service;

import com.graduation.interviewAi.domain.Interview;
import com.graduation.interviewAi.dto.AnswerWithQuestionDto;
import com.graduation.interviewAi.dto.ScoreRecordsDto;
import com.graduation.interviewAi.dto.InterviewWithAnswersDto;
import com.graduation.interviewAi.mapper.AnswerMapper;
import com.graduation.interviewAi.mapper.ResultMapper;
import com.graduation.interviewAi.mapper.InterviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultMapper resultMapper;
    private final InterviewMapper interviewMapper;
    private final AnswerMapper answerMapper;

    public List<ScoreRecordsDto> getScoreRecordsByUserId(Integer userId) {
        return resultMapper.findScoreRecordsByInterviewId(userId);
    }

    public List<InterviewWithAnswersDto> getAllRecordByUserId(Integer userId) {
        List<Interview> interviews = interviewMapper.findInterviewsByUserId(userId);
        List<InterviewWithAnswersDto> result = new ArrayList<>();

        for (Interview interview : interviews) {
            List<AnswerWithQuestionDto> answers = answerMapper.findAnswersByInterviewId(interview.getInterviewId());
            InterviewWithAnswersDto dto = new InterviewWithAnswersDto();
            BeanUtils.copyProperties(interview, dto); // 혹은 수동으로 복사
            dto.setAnswers(answers);

            result.add(dto);
        }

        return result;
    }
}
