package com.sjiwon.lectureplanner.lecture.service;

import com.sjiwon.lectureplanner.lecture.domain.Lecture;
import com.sjiwon.lectureplanner.lecture.domain.LectureRepository;
import com.sjiwon.lectureplanner.lecture.service.dto.response.LectureInformation;
import com.sjiwon.lectureplanner.lecture.service.dto.response.LectureResponse;
import com.sjiwon.lectureplanner.lecture.utils.search.Pagination;
import com.sjiwon.lectureplanner.lecture.utils.search.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureSearchService {
    private final LectureRepository lectureRepository;

    public LectureResponse findLecture(SearchCondition condition, Pageable pageable) {
        Page<Lecture> result = lectureRepository.findLectureByCondition(condition, pageable);

        List<LectureInformation> lectures = result.getContent()
                .stream()
                .map(LectureInformation::new)
                .toList();
        Pagination pagination = Pagination.of(
                result.getTotalElements(),
                result.getTotalPages(),
                pageable.getPageNumber() + 1
        );

        return new LectureResponse(lectures, pagination);
    }
}
