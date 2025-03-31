package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Resume;
import com.graduation.interviewAi.domain.ResumeList;

@Mapper
public interface ResumeMapper {
    void insertResume(Resume resume);
    void insertResumeList(ResumeList resumeList);
}
