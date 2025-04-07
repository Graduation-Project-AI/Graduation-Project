package com.graduation.interviewAi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.graduation.interviewAi.dto.QuestionDto;
import com.graduation.interviewAi.dto.ResumeListDto;
import com.graduation.interviewAi.mapper.InterviewMapper;
import com.graduation.interviewAi.mapper.QuestionMapper;
import com.graduation.interviewAi.mapper.ResumeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GptQuestionService {

    private final ResumeMapper resumeMapper;
    private final InterviewMapper interviewMapper;
    private final QuestionMapper questionMapper;
    private final GptApiService gptApiService;

    public List<String> generateAndSaveResumeQuestions(int interviewId) {
        int resumeId = interviewMapper.getResumeIdByInterviewId(interviewId);
        List<ResumeListDto> resumes = resumeMapper.getResumeListByResumeId(resumeId);

        String prompt = buildPrompt(resumes);
        List<String> gptQuestions = gptApiService.ask(prompt);

        for (String q : gptQuestions) {
            QuestionDto dto = new QuestionDto();
            int jobId = interviewMapper.getJobIdByInterviewId(interviewId);
            dto.setJobId(jobId);
            dto.setCategory(3);
            dto.setQcontent(q);
            dto.setInterviewSourceId(interviewId);
            questionMapper.insertResumeQuestion(dto);
        }

        return gptQuestions;
    }

    private String buildPrompt(List<ResumeListDto> resumes) {
        StringBuilder sb = new StringBuilder();
        sb.append("다음은 자기소개서 내용입니다. 이 내용을 바탕으로 면접 질문을 3~5개 생성해 주세요:\n\n");

        for (ResumeListDto r : resumes) {
            sb.append("- 질문: ").append(r.getResumeQuestion()).append("\n");
            sb.append("  답변: ").append(r.getResumeAnswer()).append("\n");
        }

        sb.append("\n면접 질문만 리스트로 출력해주세요.");
        return sb.toString();
    }
}
