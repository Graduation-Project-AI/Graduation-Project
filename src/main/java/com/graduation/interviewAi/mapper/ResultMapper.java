package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.Result;

@Mapper
public interface ResultMapper {
    void saveResult(Result result);
}