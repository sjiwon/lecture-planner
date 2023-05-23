package com.sjiwon.lectureplanner.lecture.infra.query;

import com.sjiwon.lectureplanner.lecture.domain.Lecture;
import com.sjiwon.lectureplanner.lecture.utils.search.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LectureDetailQueryRepository {
    Page<Lecture> findLectureByCondition(SearchCondition condition, Pageable pageable);
    List<Lecture> findByStudentId(UUID studentId);
}
