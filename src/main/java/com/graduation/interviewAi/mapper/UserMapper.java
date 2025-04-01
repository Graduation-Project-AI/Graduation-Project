package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.UserAccount;
import com.graduation.interviewAi.dto.KakaoUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserMapper {
    UserAccount findByEmail(String email);
    void insertUser(KakaoUserDto user);
}