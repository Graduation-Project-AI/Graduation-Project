package com.graduation.interviewAi.service;

import com.graduation.interviewAi.domain.Feedback;
import com.graduation.interviewAi.dto.FeedbackRequestDto;
import com.graduation.interviewAi.mapper.FeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackMapper feedbackMapper;

    public void saveFeedback(FeedbackRequestDto request) {
        Feedback feedback = new Feedback();
        feedback.setInterviewId(request.getInterviewId());
        feedback.setAiScore(request.getAiScore());
        feedback.setQScore(request.getQScore());

        System.out.println("aiScore: " + feedback.getAiScore());
        System.out.println("qScore: " + feedback.getQScore());
        
        feedbackMapper.saveFeedback(feedback);
    }
}
