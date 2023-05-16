package com.sjiwon.lectureplanner.global.exception;

import lombok.Getter;

@Getter
public class LecturePlannerException extends RuntimeException {
    private final ErrorCode code;

    public LecturePlannerException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public static LecturePlannerException type(ErrorCode code) {
        return new LecturePlannerException(code);
    }
}
