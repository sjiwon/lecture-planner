CREATE OR REPLACE PROCEDURE InsertEnroll(input_student_id IN RAW,
                                         input_lecture_id IN RAW
)
    IS
    duplicate_lecture EXCEPTION;
    exceed_limited_credits EXCEPTION;
    do_not_match_grade EXCEPTION;
    duplicate_time EXCEPTION;
    exceed_limited_number_of_student EXCEPTION;

    student_name                      STUDENT.NAME%TYPE;
    student_grade                     STUDENT.GRADE%TYPE;
    lecture_name                      LECTURE.NAME%TYPE;
    lecture_credit                    LECTURE.CREDIT%TYPE;
    lecture_day_of_week               LECTURE.DAY_OF_WEEK%TYPE;
    lecture_start_period              LECTURE.START_PERIOD%TYPE;
    lecture_grade                     LECTURE.POSSIBLE_GRADE%TYPE;

    /* {lectureId}에 대해서 수강신청한 기록 존재 Count [Exception 2 Variables] */
    number_of_enroll_specific_lecture NUMBER;

    /* 현재 {studentId}가 수강 신청한 강의들에 대한 학점 합계 [Exception 3 Variables] */
    current_enrolled_lecture_credits  NUMBER;

    /* 겹치는 강의 개수 [Exception 4 Variables] */
    duplicate_lecture_count           NUMBER;

    /* 해당 강의를 수강한 학생 수 [Exception 5 Variables] */
    current_enroll_students           NUMBER;

    /* 해당 강의 제한 학생 수 [Exception 5 Variables] */
    enroll_lecture_limited_students   NUMBER;

BEGIN
    SELECT name, grade
    INTO student_name, student_grade
    FROM STUDENT
    WHERE id = input_student_id;

    /* Exclusive Lock */
    SELECT name, credit, day_of_week, start_period, possible_grade
    INTO lecture_name, lecture_credit, lecture_day_of_week, lecture_start_period, lecture_grade
    FROM LECTURE
    WHERE id = input_lecture_id
    FOR UPDATE;

    DBMS_OUTPUT.put_line('********** 수강신청 요청 **********');
    DBMS_OUTPUT.put_line(
                '학생 -> ' ||
                TO_CHAR(student_name) ||
                '(' ||
                TO_CHAR(input_student_id) ||
                ') | ' ||
                TO_CHAR(student_grade) ||
                '학년'
        );
    DBMS_OUTPUT.put_line(
                '강의 -> ' ||
                TO_CHAR(lecture_name) ||
                '(' ||
                TO_CHAR(input_lecture_id) ||
                ') | ' ||
                TO_CHAR(lecture_credit) ||
                '학점 | ' ||
                TO_CHAR(lecture_grade) ||
                '학년 수강 가능'
        );

    /* Exception 1) 본인 학년과 맞지 않는 강의 */
    DBMS_OUTPUT.put_line(CHR(10) || '## 1. 본인 학년과 맞지 않는 강의 여부 검사 (Exception 1) ##');
    DBMS_OUTPUT.put_line('-> 본인 학년 = ' || TO_CHAR(student_grade));
    DBMS_OUTPUT.put_line('-> 강의 수강 가능 학년 = ' || TO_CHAR(lecture_grade) || ' (0이면 교양 강의)');

    IF (lecture_grade != 0 AND student_grade != lecture_grade) THEN
        RAISE do_not_match_grade;
    END IF;

    /* Exception 2) 동일 강의 중복 신청 여부 */
    SELECT COUNT(e.id)
    INTO number_of_enroll_specific_lecture
    FROM ENROLL e
    WHERE e.lecture_id = input_lecture_id
      AND e.student_id = input_student_id;

    DBMS_OUTPUT.put_line(CHR(10) || '## 2. 동일 강의 중복 신청 여부 검사 (Exception 2) ##');
    DBMS_OUTPUT.put_line('-> 중복 여부 = ' || TO_CHAR(number_of_enroll_specific_lecture) || ' (1 이상이면 중복)');

    IF (number_of_enroll_specific_lecture > 0) THEN
        RAISE duplicate_lecture;
    END IF;

    /* Exception 3) 최대학점(21학점) 초과 여부 검사 */
    SELECT SUM(l.possible_grade)
    INTO current_enrolled_lecture_credits
    FROM ENROLL e
             INNER JOIN LECTURE l on l.id = e.lecture_id
    WHERE student_id = input_student_id;

    DBMS_OUTPUT.put_line(CHR(10) || '## 3. 최대학점(21학점) 초과 여부 검사 (Exception 3) ##');
    DBMS_OUTPUT.put_line('-> 현재 신청 학점 = ' || TO_CHAR(current_enrolled_lecture_credits));
    DBMS_OUTPUT.put_line('-> 현재 신청 강의 학점 = ' || TO_CHAR(lecture_credit));

    IF (current_enrolled_lecture_credits + lecture_credit > 21) THEN
        RAISE exceed_limited_credits;
    END IF;

    /* Exception 4) 이미 등록한 강의와 겹치는 시간표 */
    SELECT COUNT(e.id)
    INTO duplicate_lecture_count
    FROM ENROLL e
             INNER JOIN LECTURE l ON l.id = e.lecture_id
    WHERE e.student_id = input_student_id
      AND l.day_of_week = lecture_day_of_week
      AND l.start_period = lecture_start_period;

    DBMS_OUTPUT.put_line(CHR(10) || '## 4. 이미 등록한 강의와 겹치는지 여부 (Exception 4) ##');
    DBMS_OUTPUT.put_line('-> 겹치는 강의 개수 = ' || TO_CHAR(duplicate_lecture_count));

    IF (duplicate_lecture_count > 0) THEN
        RAISE duplicate_time;
    END IF;

    /* Exception 5) 해당 강의 인원 초과 */
    SELECT COUNT(id)
    INTO current_enroll_students
    FROM ENROLL
    WHERE lecture_id = input_lecture_id;

    SELECT max_student
    INTO enroll_lecture_limited_students
    FROM LECTURE
    WHERE id = input_lecture_id;

    DBMS_OUTPUT.put_line(CHR(10) || '## 5. 해당 강의 인원 초과 여부 (Exception 5) ##');
    DBMS_OUTPUT.put_line('-> 현재 수강 인원 = ' || TO_CHAR(current_enroll_students));
    DBMS_OUTPUT.put_line('-> 강의 최대 수용 인원 = ' || TO_CHAR(enroll_lecture_limited_students));

    IF (current_enroll_students + 1 > enroll_lecture_limited_students) THEN
        RAISE exceed_limited_number_of_student;
    END IF;

    /* 수강 신청 : Enroll 테이블에 학번, 과목번호, 수강년도, 수강학기 입력 */
    INSERT INTO ENROLL(id, student_id, lecture_id, created_at, modified_at)
    VALUES (SYS_GUID(), input_student_id, input_lecture_id, SYSTIMESTAMP, SYSTIMESTAMP);
    COMMIT;

    DBMS_OUTPUT.put_line(CHR(10) || '수강신청 등록이 완료되었습니다.');

EXCEPTION
    WHEN do_not_match_grade THEN
        DBMS_OUTPUT.put_line(CHR(10) || '본인 학년과 맞지 않는 강의는 신청할 수 없습니다.');
        RAISE_APPLICATION_ERROR('-20100', '본인 학년과 맞지 않는 강의는 신청할 수 없습니다.');
    WHEN duplicate_lecture THEN
        DBMS_OUTPUT.put_line(CHR(10) || '동일 강의를 중복 신청할 수 없습니다.');
        RAISE_APPLICATION_ERROR('-20200', '동일 강의를 중복 신청할 수 없습니다.');
    WHEN exceed_limited_credits THEN
        DBMS_OUTPUT.put_line(CHR(10) || '최대학점(21학점)을 초과해서 신청할 수 없습니다.');
        RAISE_APPLICATION_ERROR('-20300', '최대학점(21학점)을 초과해서 신청할 수 없습니다.');
    WHEN duplicate_time THEN
        DBMS_OUTPUT.put_line(CHR(10) || '이미 등록한 강의와 시간표가 겹칩니다.');
        RAISE_APPLICATION_ERROR('-20400', '이미 등록한 강의와 시간표가 겹칩니다.');
    WHEN exceed_limited_number_of_student THEN
        DBMS_OUTPUT.put_line(CHR(10) || '해당 강의 수강인원이 초과되었습니다.');
        RAISE_APPLICATION_ERROR('-20500', '해당 강의 수강인원이 초과되었습니다.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.put_line(CHR(10) || 'SQLCODE -> ' || SQLCODE);
        DBMS_OUTPUT.put_line('SQLERRM -> ' || SQLERRM);
        RAISE_APPLICATION_ERROR('-20600', '서버 내부 오류');
END ;
