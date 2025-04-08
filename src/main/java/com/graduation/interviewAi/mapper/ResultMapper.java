package com.graduation.interviewAi.mapper;

import com.graduation.interviewAi.domain.Result;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ResultMapper {
    void saveResult(Result result);
    Result findResultByInterviewId(int interviewId);
}
