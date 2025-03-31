-- UserAccount 테이블 (사용자 정보)
CREATE TABLE UserAccount (
    userId INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NULL,
    email VARCHAR(50) NOT NULL COMMENT '카카오 로그인에 따른 카카오 ID',
    role INT NOT NULL CHECK (role IN (1, 2)) COMMENT '1: 구직자, 2: 취업자',
    PRIMARY KEY (userId)
);

-- Field 테이블 (직무 정보)
CREATE TABLE Field (
    jobId INT NOT NULL AUTO_INCREMENT,
    job VARCHAR(50) NOT NULL,
    PRIMARY KEY (jobId)
);

-- Interview 테이블 (인터뷰 정보)
CREATE TABLE Interview (
    interviewid INT NOT NULL AUTO_INCREMENT,
    company VARCHAR(50) NULL,
    jobId INT NOT NULL,
    resumeId INT NOT NULL,
    date TIMESTAMP NULL,
    userId INT NOT NULL,
    PRIMARY KEY (interviewid),
    FOREIGN KEY (userId) REFERENCES UserAccount (userId),
    FOREIGN KEY (jobId) REFERENCES Field (jobId)
);

-- Question 테이블 (질문 정보)
CREATE TABLE Question (
    questionId INT NOT NULL AUTO_INCREMENT,
    jobId INT NOT NULL,
    category INT NULL,
    qcontent TEXT NULL,
    PRIMARY KEY (questionId),
    FOREIGN KEY (jobId) REFERENCES Field (jobId)
);

-- Answer 테이블 (답변 정보)
CREATE TABLE Answer (
    answerId INT NOT NULL AUTO_INCREMENT,
    questionId INT NOT NULL,
    acontent TEXT NULL,
    interviewid INT NOT NULL,
    PRIMARY KEY (answerId),
    FOREIGN KEY (questionId) REFERENCES Question (questionId),
    FOREIGN KEY (interviewid) REFERENCES Interview (interviewid)
);

-- Result 테이블 (인터뷰 결과)
CREATE TABLE Result (
    resultId INT NOT NULL AUTO_INCREMENT,
    logicScore INT NULL,
    simScore INT NULL,
    claScore INT NULL,
    createdAt TIMESTAMP NULL,
    totalScore INT NULL,
    interviewId INT NOT NULL,
    suggestion TEXT NULL,
    allEval TEXT NULL,
    impAnswer TEXT NULL,
    PRIMARY KEY (resultId),
    FOREIGN KEY (interviewId) REFERENCES Interview (interviewid)
);

-- Feedback 테이블 (AI 피드백)
CREATE TABLE Feedback (
    feedbackId INT NOT NULL AUTO_INCREMENT,
    aiScore INT NULL,
    interviewid INT NOT NULL,
    qScore INT NULL,
    PRIMARY KEY (feedbackId),
    FOREIGN KEY (interviewid) REFERENCES Interview (interviewid)
);

-- Resume 테이블 (이력서)
CREATE TABLE Resume (
    resumeId INT NOT NULL AUTO_INCREMENT,
    userId INT NOT NULL,
    PRIMARY KEY (resumeId),
    FOREIGN KEY (userId) REFERENCES UserAccount (userId)
);

-- ResumeList 테이블 (이력서 상세 질문 및 답변)
CREATE TABLE ResumeList (
    resumeListId INT NOT NULL AUTO_INCREMENT,
    resumeId INT NOT NULL,
    resumeQuestion TEXT NULL,
    resumeAnswer TEXT NULL,
    PRIMARY KEY (resumeListId),
    FOREIGN KEY (resumeId) REFERENCES Resume (resumeId)
);

-- Post 테이블 (게시글)
CREATE TABLE Post (
    postId INT NOT NULL AUTO_INCREMENT,
    postName VARCHAR(100) NULL,
    pContent TEXT NULL,
    createdAt TIMESTAMP NULL,
    userId INT NOT NULL,
    PRIMARY KEY (postId),
    FOREIGN KEY (userId) REFERENCES UserAccount (userId)
);

-- Comment 테이블 (댓글)
CREATE TABLE Comment (
    commentId INT NOT NULL AUTO_INCREMENT,
    postId INT NOT NULL,
    cContent TEXT NULL,
    userId INT NOT NULL,
    createdAt TIMESTAMP NULL,
    PRIMARY KEY (commentId),
    FOREIGN KEY (postId) REFERENCES Post (postId),
    FOREIGN KEY (userId) REFERENCES UserAccount (userId)
);