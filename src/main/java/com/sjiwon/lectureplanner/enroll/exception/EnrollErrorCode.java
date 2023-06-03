package com.sjiwon.lectureplanner.enroll.exception;

import com.sjiwon.lectureplanner.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EnrollErrorCode implements ErrorCode {
    DO_NOT_MATCH_GRADE(HttpStatus.CONFLICT, "ENROLL_001", "본인 학년과 맞지 않는 강의는 신청할 수 없습니다."),
    DUPLICATE_LECTURE_ENROLL(HttpStatus.CONFLICT, "ENROLL_002", "동일 강의를 중복 신청할 수 없습니다."),
    EXCEED_LIMITED_CREDITS(HttpStatus.CONFLICT, "ENROLL_003", "최대학점(21학점)을 초과해서 신청할 수 없습니다."),
    DUPLICATE_TIME_SCHEDULE(HttpStatus.CONFLICT, "ENROLL_004", "이미 등록한 강의와 시간표가 겹칩니다."),
    EXCEED_LIMITED_NUMBER_OF_STUDENTS(HttpStatus.CONFLICT, "ENROLL_005", "해당 강의 수강인원이 초과되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ENROLL_006", "서버 내부 에러가 발생했습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
