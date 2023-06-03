package com.sjiwon.lectureplanner.enroll.service;

import com.sjiwon.lectureplanner.enroll.domain.EnrollRepository;
import com.sjiwon.lectureplanner.enroll.exception.EnrollErrorCode;
import com.sjiwon.lectureplanner.global.exception.LecturePlannerException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.UUID;

import static com.sjiwon.lectureplanner.enroll.exception.EnrollErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnrollService {
    private final EnrollRepository enrollRepository;

    @Transactional
    public void enroll(UUID lectureId, UUID studentId) {
        try {
            enrollRepository.enrollLecture(studentId, lectureId);
        } catch (DataAccessException exception) {
            if (exception.getCause().getCause() instanceof SQLException ex) {
                switch (ex.getErrorCode()) {
                    case 20100 -> throw LecturePlannerException.type(DO_NOT_MATCH_GRADE);
                    case 20200 -> throw LecturePlannerException.type(DUPLICATE_LECTURE_ENROLL);
                    case 20300 -> throw LecturePlannerException.type(EXCEED_LIMITED_CREDITS);
                    case 20400 -> throw LecturePlannerException.type(DUPLICATE_TIME_SCHEDULE);
                    case 20500 -> throw LecturePlannerException.type(EXCEED_LIMITED_NUMBER_OF_STUDENTS);
                    default -> throw LecturePlannerException.type(EnrollErrorCode.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    @Transactional
    public void cancelEnroll(UUID lectureId, UUID studentId) {
        enrollRepository.deleteEnroll(lectureId, studentId);
    }
}
