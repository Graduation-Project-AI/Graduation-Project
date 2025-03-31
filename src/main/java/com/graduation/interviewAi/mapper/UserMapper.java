package com.graduation.interviewAi.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.graduation.interviewAi.domain.UserAccount;

@Mapper
public interface UserMapper {
    UserAccount findByEmail(String email);
    void insertUser(UserAccount user);
}