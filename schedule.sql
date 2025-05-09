USE schedule;

-- Users 테이블 먼저 생성
CREATE TABLE users (
     user_id     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '유저 식별자',
     nickname    VARCHAR(100) NOT NULL COMMENT '닉네임',
     login_id    VARCHAR(100) NOT NULL UNIQUE COMMENT '유저 아이디',
     password    VARCHAR(100) COMMENT '비밀번호',
     createDate  DATETIME COMMENT '계정 생성 날짜',
     updateDate  DATETIME COMMENT '계정 수정 날짜'
);

-- Schedule 테이블
CREATE TABLE schedule (
    schedule_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 고유 식별 번호',
    user_id       BIGINT COMMENT '유저 고유 식별 번호',
    title         VARCHAR(100) NOT NULL COMMENT '제목',
    contents      TEXT COMMENT '내용',
    createDate    DATETIME COMMENT '생성 날짜',
    updateDate    DATETIME COMMENT '수정 날짜',

    CONSTRAINT fk_schedule_user
        FOREIGN KEY (user_id) REFERENCES users(userId)
            ON DELETE CASCADE
);