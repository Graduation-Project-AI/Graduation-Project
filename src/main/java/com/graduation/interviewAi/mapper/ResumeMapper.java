package com.graduation.interviewAi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Resume;
import com.graduation.interviewAi.domain.ResumeList;
import com.graduation.interviewAi.dto.ResumeListDto;

@Mapper
public interface ResumeMapper {
    void insertResume(Resume resume);
    void insertResumeList(ResumeList resumeList);
    List<ResumeListDto> getResumeListByResumeId(int resumeId);
}
