package com.sjiwon.lectureplanner.auth.exception;

import com.sjiwon.lectureplanner.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_001", "학생 정보가 존재하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_002", "비밀번호가 일치하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
