package com.sjiwon.lectureplanner.lecture.infra.query;

import com.sjiwon.lectureplanner.lecture.domain.Lecture;
import com.sjiwon.lectureplanner.lecture.utils.search.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureDetailQueryRepository {
    Page<Lecture> findLectureByCondition(SearchCondition condition, Pageable pageable);
}
