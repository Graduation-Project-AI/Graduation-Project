package com.graduation.interviewAi.mapper;

import com.graduation.interviewAi.domain.Result;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResultMapper {
    void saveResult(Result result);
    List<Result> findResultByInterviewId(int interviewId);
}
