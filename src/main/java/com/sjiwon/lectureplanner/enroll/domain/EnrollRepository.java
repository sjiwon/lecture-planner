package com.sjiwon.lectureplanner.enroll.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface EnrollRepository extends JpaRepository<Enroll, UUID> {
    @Procedure(procedureName = "InsertEnroll")
    void enrollLecture(@Param("input_student_id") UUID inputStudentId,
                       @Param("input_lecture_id") UUID inputLectureId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Enroll e WHERE e.lectureId = :lectureId AND e.studentId = :studentId")
    void deleteEnroll(@Param("lectureId") UUID lectureId, @Param("studentId") UUID studentId);
}
