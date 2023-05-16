package com.sjiwon.lectureplanner.auth.service;

import com.sjiwon.lectureplanner.auth.exception.AuthErrorCode;
import com.sjiwon.lectureplanner.auth.service.dto.response.StudentPrincipal;
import com.sjiwon.lectureplanner.global.exception.LecturePlannerException;
import com.sjiwon.lectureplanner.student.domain.Student;
import com.sjiwon.lectureplanner.student.domain.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;

    public StudentPrincipal login(String loginId, String password) {
        Student student = getStudent(loginId);
        validatePassword(student, password);

        return new StudentPrincipal(student.getId(), student.getName(), student.getGrade());
    }

    private Student getStudent(String loginId) {
        return studentRepository.findByLoginId(loginId)
                .orElseThrow(() -> LecturePlannerException.type(AuthErrorCode.STUDENT_NOT_FOUND));
    }

    private void validatePassword(Student student, String password) {
        if (!student.getPassword().equals(password)) {
            throw LecturePlannerException.type(AuthErrorCode.INVALID_PASSWORD);
        }
    }
}
