package com.graduation.interviewAi.service;

import com.graduation.interviewAi.dto.ScoreRecordsDto;
import com.graduation.interviewAi.mapper.ResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultMapper resultMapper;

    public List<ScoreRecordsDto> getScoreRecordsByUserId(Integer userId) {
        return resultMapper.findScoreRecordsByInterviewId(userId);
    }
}
