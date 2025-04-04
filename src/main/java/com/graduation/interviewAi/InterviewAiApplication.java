package com.graduation.interviewAi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.graduation.interviewAi.mapper")
public class InterviewAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewAiApplication.class, args);
	}

}
