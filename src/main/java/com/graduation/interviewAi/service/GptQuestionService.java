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
        sb.append("다음은 한 지원자의 자기소개서 답변 내용입니다. 각 답변을 바탕으로 실제 면접에서 사용할 수 있는 창의적이고 깊이 있는 질문과 "
        		+ "각 답변에 포함된 기술 키워드를 파악하여 관련된 기술 면접 질문을 총 7개 생성해주세요.\n");
        sb.append("질문은 기존 자기소개서 질문과 중복되지 않도록 해주세요.\n");
        sb.append("답변의 내용, 경험, 태도 등을 바탕으로 면접에서 확인하고 싶은 사항을 중심으로 구성해 주세요.\n\n");

        for (ResumeListDto r : resumes) {
            sb.append("- 자기소개서 답변: ").append(r.getResumeAnswer()).append("\n\n");
        }

        sb.append("\n면접 질문만 리스트로 출력해주세요.");
        return sb.toString();
    }
}
