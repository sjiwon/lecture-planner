DROP TABLE ENROLL;
DROP TABLE STUDENT;
DROP TABLE LECTURE;
DROP TABLE PROFESSOR;

-- 교수 테이블
CREATE TABLE PROFESSOR
(
    id          RAW(16)      NOT NULL,        -- ID(PK)
    name        VARCHAR2(50) NOT NULL UNIQUE, -- 교수 이름
    created_at  TIMESTAMP    NOT NULL,        -- 생성 날짜
    modified_at TIMESTAMP    NOT NULL,        -- 수정 날짜

    PRIMARY KEY (id)
);

-- 강의 테이블
CREATE TABLE LECTURE
(
    id             RAW(16)      NOT NULL, -- ID(PK)
    professor_id   RAW(16)      NOT NULL, -- 해당 강의의 교수 ID(FK)
    name           VARCHAR2(50) NOT NULL, -- 강의명
    credit         NUMBER(1)    NOT NULL, -- 강의 학점
    day_of_week    NUMBER(1)    NOT NULL, -- 강의 요일
    start_period   NUMBER(1)    NOT NULL, -- 강의 시작교시
    end_period     NUMBER(1)    NOT NULL, -- 강의 종료교시
    possible_grade NUMBER(1)    NOT NULL, -- 강의 수강 가능 학년
    max_student    NUMBER(2)    NOT NULL, -- 강의 최대 인원

    PRIMARY KEY (id),
    UNIQUE (professor_id, day_of_week, start_period, end_period)
);

-- 학생 테이블
CREATE TABLE STUDENT
(
    id          RAW(16)      NOT NULL,        -- ID(PK)
    login_id    VARCHAR2(50) NOT NULL UNIQUE, -- 로그인 아이디
    password    VARCHAR2(50) NOT NULL,        -- 로그인 비밀번호
    name        VARCHAR2(50) NOT NULL,        -- 학생 이름
    grade       NUMBER(1)    NOT NULL,        -- 학생 학년
    created_at  TIMESTAMP    NOT NULL,        -- 생성 날짜
    modified_at TIMESTAMP    NOT NULL,        -- 수정 날짜

    PRIMARY KEY (id)
);

-- 수강신청 테이블
CREATE TABLE ENROLL
(
    id          RAW(16)   NOT NULL, -- ID(PK)
    student_id  RAW(16)   NOT NULL, -- 수강신청한 학생 ID(FK)
    lecture_id  RAW(16)   NOT NULL, -- 수강신청한 강의 ID(FK)
    created_at  TIMESTAMP NOT NULL, -- 신청 날짜
    modified_at TIMESTAMP NOT NULL, -- 수정 날짜

    PRIMARY KEY (id),
    UNIQUE (student_id, lecture_id)
);

-- FK Constraint
ALTER TABLE lecture
ADD CONSTRAINT lecture_professor_professor_id_fk
FOREIGN KEY (professor_id)
REFERENCES professor;

ALTER TABLE enroll
ADD CONSTRAINT enroll_student_student_id_fk
FOREIGN KEY (student_id)
REFERENCES student;

ALTER TABLE enroll
ADD CONSTRAINT enroll_lecture_lecture_id_fk
FOREIGN KEY (lecture_id)
REFERENCES lecture;
