package com.sjiwon.lectureplanner.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(LecturePlannerException.class)
    public ResponseEntity<ErrorResponse> lecturPlannerException(LecturePlannerException exception) {
        ErrorCode code = exception.getCode();
        return ResponseEntity
                .status(code.getStatus())
                .body(ErrorResponse.from(code));
    }

    /**
     * 요청 데이터 Validation 전용 ExceptionHandler (@RequestBody)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) throws JsonProcessingException {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return convert(GlobalErrorCode.VALIDATION_ERROR, extractErrorMessage(fieldErrors));
    }

    /**
     * 요청 데이터 Validation 전용 ExceptionHandler (@ModelAttribute)
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindException(BindException e) throws JsonProcessingException {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return convert(GlobalErrorCode.VALIDATION_ERROR, extractErrorMessage(fieldErrors));
    }

    private String extractErrorMessage(List<FieldError> fieldErrors) throws JsonProcessingException {
        if (fieldErrors.size() == 1) {
            return fieldErrors.get(0).getDefaultMessage();
        }

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return objectMapper.writeValueAsString(errors);
    }

    private ResponseEntity<ErrorResponse> convert(ErrorCode code) {
        logging(code);
        return ResponseEntity
                .status(code.getStatus())
                .body(ErrorResponse.from(code));
    }

    private ResponseEntity<ErrorResponse> convert(ErrorCode code, String message) {
        logging(code, message);
        return ResponseEntity
                .status(code.getStatus())
                .body(ErrorResponse.of(code, message));
    }

    private void logging(ErrorCode code) {
        log.warn("{} | {} | {}", code.getStatus(), code.getErrorCode(), code.getMessage());
    }

    private void logging(ErrorCode code, String message) {
        log.warn("{} | {} | {}", code.getStatus(), code.getErrorCode(), message);
    }
}
