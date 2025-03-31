package com.graduation.interviewAi.service;

import org.springframework.stereotype.Service;

import com.graduation.interviewAi.domain.Interview;
import com.graduation.interviewAi.domain.Resume;
import com.graduation.interviewAi.domain.ResumeList;
import com.graduation.interviewAi.dto.InterviewStartRequest;
import com.graduation.interviewAi.dto.ResumeListDto;
import com.graduation.interviewAi.mapper.InterviewMapper;
import com.graduation.interviewAi.mapper.ResumeMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewMapper interviewMapper;
    private final ResumeMapper resumeMapper;

    public Integer startInterview(InterviewStartRequest request) {
        // 1. Resume 생성
        Resume resume = new Resume();
        resume.setUserId(request.getUserId());
        resumeMapper.insertResume(resume);	//resumeId 생성됨

        // 2. ResumeList 생성 (여러개 삽입)
        for (ResumeListDto dto : request.getResume()) {
            ResumeList rl = new ResumeList();
            rl.setResumeId(resume.getResumeId());
            rl.setResumeQuestion(dto.getResumeQuestion());
            rl.setResumeAnswer(dto.getResumeAnswer());
            resumeMapper.insertResumeList(rl);
        }

        // 3. Interview 생성
        Interview interview = new Interview();
        interview.setCompany(request.getCompany());
        interview.setJobId(request.getJobId());
        interview.setResumeId(resume.getResumeId());
        interview.setUserId(request.getUserId());

        interviewMapper.insertInterview(interview);	//interviewId 생성됨

        return interview.getInterviewId();
    }
}

